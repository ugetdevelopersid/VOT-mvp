from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_POST
import json
from openai import OpenAI

from .models import UserProfile
from django.http import HttpResponse
import base64
import os

# Create your views here.
prompt_template = (
    "You are a prompt engineer. When given a free-form description of an outfit, do the following:\n"
    "    1. **Classify** the description into one of two cases: \n"
    "        - **TOP+BOTTOM** if it mentions two pieces (e.g. “crop top and trousers,” “blouse with skirt,” etc.)\n"
    "        - **DRESS** if it mentions a single-piece garment (e.g. “maxi dress,” “sheath dress,” etc.)\n"
    "    2. **Extract metadata** based on the case:\n"
    "        - **If TOP+BOTTOM**, pull out: • UPPER_COLOR • UPPER_STYLE • UPPER_GARMENT • LOWER_COLOR • LOWER_STYLE • LOWER_GARMENT • ACCESSORY_LIST\n"
    "        - **If DRESS**, pull out: • DRESS_COLOR • DRESS_STYLE • DRESS_TYPE (e.g. “wrap dress,” “sheath dress,” etc.) • ACCESSORY_LIST\n"
    "    3. **Emit exactly one DALL·E prompt and give me back the image generated from that prompt** using the matching template:\n"
    "        - **TOP+BOTTOM template:**Full-body portrait of a {AGE}-year-old {GENDER} mannequin with an {BODY_SHAPE} figure and {BODY_SIZE} build and height {HEIGHT}cm, {SKIN_TONE} skin tone, and a smooth, featureless {FACE_SHAPE} face, {HAIR_LENGTH}-length wavy hair; wearing a {{UPPER_COLOR}} {{UPPER_STYLE}} {{UPPER_GARMENT}} paired with a {{LOWER_COLOR}} {{LOWER_STYLE}} {{LOWER_GARMENT}}, accessorized with {{ACCESSORY_LIST}}. She stands straight with relaxed shoulders and arms at sides (toes pointing forward, ~5 cm from torso), under soft even studio lighting (key softbox at 45° camera-left; fill light at 60%% intensity camera-right), shot with an 85 mm lens at a height of 165 cm, against a plain white background, in high-resolution fashion-catalog quality.\n"
    "        - **DRESS template:**Full-body portrait of a {AGE}-year-old {GENDER} mannequin with an {BODY_SHAPE} figure and {BODY_SIZE} build and height {HEIGHT}cm, {SKIN_TONE} skin tone, and a smooth, featureless {FACE_SHAPE} face, {HAIR_LENGTH}-length wavy hair; wearing a {{DRESS_COLOR}} {{DRESS_STYLE}} {{DRESS_TYPE}}, accessorized with {{ACCESSORY_LIST}}. She stands straight with relaxed shoulders and arms at sides (toes pointing forward, ~5 cm from torso), under soft even studio lighting (key softbox at 45° camera-left; fill light at 60%% intensity camera-right), shot with an 85 mm lens at a height of 165 cm, against a plain white background, in high-resolution fashion-catalog quality.\n"
)

client = OpenAI(api_key=os.environ.get('OPENAI_API_KEY'))

@csrf_exempt
@require_POST
def create_user_profile(request):
    try:
        data = json.loads(request.body)
        user = UserProfile.objects.create(
            email=data['email'],
            age=data['age'],
            gender=data['gender'],
            skin_tone=data['skin_tone'],
            height=data['height'],
            body_shape=data['body_shape'],
            body_size=data['body_size'],
            face_shape=data['face_shape'],
            hair_length=data['hair_length'],
            hair_texture=data['hair_texture']
        )
        return JsonResponse({'status': 'success', 'id': user.id})
    except Exception as e:
        return JsonResponse({'status': 'error', 'message': str(e)}, status=400)

@csrf_exempt
@require_POST
def get_image_by_email(request):
    try:
        data = request.POST
        image = request.FILES.get('image')
        # Read the uploaded image and encode it to base64
        image_content = image.read()
        image_b64 = base64.b64encode(image_content).decode('utf-8')
        email = data.get('email')
        if not email or not image:
            return JsonResponse({'status': 'error', 'message': 'Email and image (base64) are required.'}, status=400)
        user = UserProfile.objects.get(email=email)
        # Fill in user profile data
        prompt = prompt_template.format(
            AGE=user.age,
            GENDER=user.gender,
            SKIN_TONE=user.skin_tone,
            HEIGHT=user.height,
            BODY_SHAPE=user.body_shape,
            BODY_SIZE=user.body_size,
            FACE_SHAPE=user.face_shape,
            HAIR_LENGTH=user.hair_length,
            HAIR_TEXTURE=user.hair_texture,
        )

        response = client.responses.create(
            model="gpt-4.1-mini",
            input=[
                {
                    "role": "user",
                    "content": [
                        { "type": "input_text", "text": prompt },
                        {
                            "type": "input_image",
                            "image_url": f"data:image/jpeg;base64,{image_b64}",
                        },
                    ],
                }
            ],
            tools=[{"type": "image_generation"}],
        )

        # print(response)

        image_data = [
            output.result
            for output in response.output
            if output.type == "image_generation_call"
        ]

        if image_data:
            image_base64 = image_data[0]
            return HttpResponse(base64.b64decode(image_base64), content_type="image/png")

        return JsonResponse({'status': 'internal server error', 'message': 'Could not generate image.'}, status=500)
    except UserProfile.DoesNotExist:
        return JsonResponse({'status': 'error', 'message': 'User not found.'}, status=404)
    except Exception as e:
        return JsonResponse({'status': 'error', 'message': str(e)}, status=400)

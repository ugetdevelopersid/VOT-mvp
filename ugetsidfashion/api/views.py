from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_POST
import json
import openai
from .models import UserProfile

# Create your views here.

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
        data = json.loads(request.body)
        email = data.get('email')
        image_b64 = data.get('image')
        if not email or not image_b64:
            return JsonResponse({'status': 'error', 'message': 'Email and image (base64) are required.'}, status=400)
        user = UserProfile.objects.get(email=email)
        # Prepare prompt template
        prompt_template = '''Full-body portrait of a {AGE}-year-old {GENDER} mannequin with {SKIN_TONE} skin tone, {HEIGHT_CM} cm tall, {BODY_SHAPE} figure and {BODY_SIZE} build, a smooth featureless {FACE_SHAPE} face (no eyes, nose, mouth, or eyebrows), and {HAIR_LENGTH} {HAIR_TEXTURE} hair framing the head. She wears a {FIT_UPPER} {UPPER_GARMENT} {UPPER_COLOR} {UPPER_STYLE}, tucked into a {FIT_LOWER} {LOWER_GARMENT} {LOWER_COLOR} {LOWER_STYLE}, accessorized with {ACCESSORY_LIST}. She stands straight with relaxed shoulders and arms at sides (toes pointing forward, ~{ARM_DISTANCE_CM} cm from torso), under soft even studio lighting (key softbox at {KEY_LIGHT_ANGLE}° camera-left; fill light at {FILL_LIGHT_INTENSITY}% intensity camera-right), shot with an {LENS_FOCAL_LENGTH} mm lens at a camera height of {CAMERA_HEIGHT_CM} cm, against a plain white background in high-resolution fashion-catalog quality.'''
        # Fill in user profile data
        prompt = prompt_template.format(
            AGE=user.age,
            GENDER=user.gender,
            SKIN_TONE=user.skin_tone,
            HEIGHT_CM=user.height,
            BODY_SHAPE=user.body_shape,
            BODY_SIZE=user.body_size,
            FACE_SHAPE=user.face_shape,
            HAIR_LENGTH=user.hair_length,
            HAIR_TEXTURE=user.hair_texture,
            FIT_UPPER="fitted", UPPER_GARMENT="top", UPPER_COLOR="black", UPPER_STYLE="casual",
            FIT_LOWER="slim", LOWER_GARMENT="pants", LOWER_COLOR="blue", LOWER_STYLE="jeans",
            ACCESSORY_LIST="none", ARM_DISTANCE_CM=10, KEY_LIGHT_ANGLE=45, FILL_LIGHT_INTENSITY=50,
            LENS_FOCAL_LENGTH=85, CAMERA_HEIGHT_CM=150
        )
        # Call OpenAI API (replace 'your-openai-key' with your actual key)
        openai.api_key = 'your-openai-key'
        response = openai.Image.create(
            prompt=prompt,
            n=1,
            size="512x512",
            response_format="b64_json",
            image=image_b64  # Pass the client's image as context
        )
        result_image_b64 = response['data'][0]['b64_json']
        return JsonResponse({'status': 'success', 'image': result_image_b64})
    except UserProfile.DoesNotExist:
        return JsonResponse({'status': 'error', 'message': 'User not found.'}, status=404)
    except Exception as e:
        return JsonResponse({'status': 'error', 'message': str(e)}, status=400)

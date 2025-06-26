from django.urls import path
from .views import create_user_profile, get_image_by_email

urlpatterns = [
    path('create-profile/', create_user_profile, name='create_user_profile'),
    path('get-image/', get_image_by_email, name='get_image_by_email'),
]

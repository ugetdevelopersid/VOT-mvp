from django.db import models

# Create your models here.

class UserProfile(models.Model):
    email = models.EmailField(unique=True)
    age = models.PositiveIntegerField()
    gender = models.CharField(max_length=20)
    skin_tone = models.CharField(max_length=50)
    height = models.PositiveIntegerField()  # changed from FloatField to PositiveIntegerField
    body_shape = models.CharField(max_length=50)
    body_size = models.CharField(max_length=50)
    face_shape = models.CharField(max_length=50)
    hair_length = models.CharField(max_length=50)
    hair_texture = models.CharField(max_length=50)

    def __str__(self):
        return self.email

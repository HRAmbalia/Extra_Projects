from django.db import models

# Create your models here.
from django.db import models
from django.conf import settings

class Movie(models.Model):
    title = models.CharField(max_length=100)
    description = models.TextField()
    release_year = models.IntegerField()
    is_rented = models.BooleanField(default=False)

    def __str__(self):
        return self.title

class Rental(models.Model):
    movie = models.ForeignKey(Movie, on_delete=models.CASCADE)
    rented_by = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    rented_to = models.CharField(max_length=100, default="None")

    def __str__(self):
        return f"{self.movie.title} rented by {self.rented_by.username} to {self.rented_to}"

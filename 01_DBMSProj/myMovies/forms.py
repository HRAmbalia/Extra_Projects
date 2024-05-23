from django import forms
from .models import Movie, Rental

class MovieForm(forms.ModelForm):
    class Meta:
        model = Movie
        fields = ['title', 'description', 'release_year']

class RentalForm(forms.Form):
    rented_to = forms.CharField(max_length=100, label='Rented to')

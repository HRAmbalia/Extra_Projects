from django.shortcuts import render, get_object_or_404, redirect
from .models import Movie, Rental
from django.contrib.auth.decorators import login_required
from .forms import MovieForm, RentalForm
from django.http import HttpResponseNotAllowed
from django.utils import timezone 

@login_required
def movie_list(request):
    movies = Movie.objects.all()
    latest_rental = Rental.objects.last()
    latest_rented_to = latest_rental.rented_to if latest_rental else None
    return render(request, 'movie_list.html', {'movies': movies, 'latest_rented_to': latest_rented_to})


@login_required
def rent_movie(request, movie_id):
    movie = get_object_or_404(Movie, pk=movie_id)
    if movie.is_rented:
        return redirect('movie_list')
    latest_rental = Rental.objects.last()
    latest_rented_to = latest_rental.rented_to if latest_rental else None
    if request.method == 'POST':
        form = RentalForm(request.POST)
        if form.is_valid():
            rented_to = form.cleaned_data['rented_to']
            rental = Rental(movie=movie, rented_by=request.user, rented_to=rented_to)
            rental.save()
            movie.is_rented = True
            movie.save()  # Update is_rented field
            return redirect('movie_list')
    else:
        form = RentalForm()
    return render(request, 'rent_movie.html', {'form': form, 'movie': movie, 'latest_rented_to': latest_rented_to})


@login_required
def update_movie(request, movie_id):
    movie = get_object_or_404(Movie, pk=movie_id)
    if request.method == 'POST':
        form = MovieForm(request.POST, instance=movie)
        if form.is_valid():
            movie = form.save(commit=False)
            movie.save()
            return redirect('movie_list')
    else:
        form = MovieForm(instance=movie)
    return render(request, 'update_movie.html', {'form': form, 'movie': movie})

@login_required
def add_movie(request):
    if request.method == 'POST':
        form = MovieForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('movie_list')
    else:
        form = MovieForm()
    return render(request, 'add_movie.html', {'form': form})


@login_required  # Add this decorator
def delete_movie(request, movie_id):
    movie = get_object_or_404(Movie, pk=movie_id)
    if request.method == 'POST':
        movie.delete()
        return redirect('movie_list')
    return render(request, 'delete_movie.html', {'movie': movie})

def rented_movies(request):
    rented_movies = Movie.objects.filter(is_rented=True)
    return render(request, 'rented_movies.html', {'rented_movies': rented_movies})

@login_required
def return_movie(request, movie_id):
    movie = get_object_or_404(Movie, pk=movie_id)
    if movie.is_rented:
        movie.is_rented = False
        movie.save()
        return redirect('movie_list')
    return HttpResponseNotAllowed(['POST'])

@login_required
def movie_detail(request, movie_id):
    movie = get_object_or_404(Movie, pk=movie_id)
    return render(request, 'movie_detail.html', {'movie': movie})

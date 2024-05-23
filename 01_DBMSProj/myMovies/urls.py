from django.urls import path
from . import views

urlpatterns = [
    path('list_movie/', views.movie_list, name='movie_list'),
    path('add_movie/', views.add_movie, name='add_movie'),
    path('rent_movie/<int:movie_id>/', views.rent_movie, name='rent_movie'),
    path('update_movie/<int:movie_id>/', views.update_movie, name='update_movie'),
    path('delete_movie/<int:movie_id>/', views.delete_movie, name='delete_movie'),    
    path('rented_movies/', views.rented_movies, name='rented_movies'), 
    path('return/<int:movie_id>/', views.return_movie, name='return_movie'),
    path('movie/<int:movie_id>/', views.movie_detail, name='movie_detail')
]

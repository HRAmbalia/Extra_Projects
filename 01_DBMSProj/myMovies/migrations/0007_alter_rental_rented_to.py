# Generated by Django 4.1.5 on 2024-04-24 08:43

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myMovies', '0006_alter_rental_rented_to'),
    ]

    operations = [
        migrations.AlterField(
            model_name='rental',
            name='rented_to',
            field=models.CharField(default='None', max_length=100),
        ),
    ]

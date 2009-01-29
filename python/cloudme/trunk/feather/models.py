from django.db import models

class Sample(models.Model):
    weight = models.FloatField()
    date = models.DateTimeField(auto_now=True)
    
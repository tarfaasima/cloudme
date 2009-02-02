from django.db import models

class Sample(models.Model):
    weight = models.FloatField()
    date = models.DateTimeField(auto_now=True)
    
    #def __init__(self, weight):
    #    self.weight = weight
    
    def __unicode__(self):
        return self.date.strftime('%Y-%m-%d %H:%M') + ' (%.01f kg)' % self.weight
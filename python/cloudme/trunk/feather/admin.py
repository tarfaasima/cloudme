from cloudme.feather.models import Sample
from django.contrib import admin

class SampleAdmin(admin.ModelAdmin):
    list_display = ('weight', 'date')
    date_hierarchy = 'date'

admin.site.register(Sample, SampleAdmin)

from django.http import HttpResponse
from cloudme.feather.models import Sample

def index(request):
    return HttpResponse("Hello world!")
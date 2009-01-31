from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from cloudme.feather.models import Sample

def index(request):
    if (not request.user.is_authenticated()):
        return HttpResponseRedirect('login')
    samples  = Sample.objects.all().order_by('-date')
    return render_to_response('feather/index.html', {'samples' : samples})

def new(request):
    if (not request.user.is_authenticated()):
        return HttpResponseRedirect('login')
    return render_to_response('feather/new.html')

def save(request):
    if (not request.user.is_authenticated()):
        return HttpResponseRedirect('login')
    sample = Sample(weight=request.REQUEST['weight'])
    sample.save()
    return HttpResponseRedirect('..')

def login(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    login(request, user)
    return HttpResponseRedirect('..')

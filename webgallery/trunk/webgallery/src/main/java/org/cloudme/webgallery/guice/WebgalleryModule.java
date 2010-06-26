// Copyright 2010 Moritz Petersen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.cloudme.webgallery.guice;

import org.cloudme.webgallery.cache.CacheService;
import org.cloudme.webgallery.cache.gae.GaeCacheService;
import org.cloudme.webgallery.image.ImageService;
import org.cloudme.webgallery.image.gae.GaeImageService;
import org.cloudme.webgallery.persistence.AlbumRepository;
import org.cloudme.webgallery.persistence.FlickrMetaDataRepository;
import org.cloudme.webgallery.persistence.PhotoDataRepository;
import org.cloudme.webgallery.persistence.PhotoRepository;
import org.cloudme.webgallery.persistence.ScaledPhotoDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyAlbumRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyFlickrMetaDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoDataRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyPhotoRepository;
import org.cloudme.webgallery.persistence.objectify.ObjectifyScaledPhotoDataRepository;
import org.cloudme.webgallery.service.AlbumService;
import org.cloudme.webgallery.service.FlickrService;
import org.cloudme.webgallery.service.PhotoDataService;
import org.cloudme.webgallery.service.PhotoService;
import org.cloudme.webgallery.service.ScaledPhotoDataService;

import com.google.inject.AbstractModule;

public class WebgalleryModule extends AbstractModule {
    @Override
    public void configure() {
        bind(AlbumService.class);
        bind(FlickrService.class);
        bind(PhotoDataService.class);
        bind(PhotoService.class);
        bind(ScaledPhotoDataService.class);
        bind(CacheService.class).to(GaeCacheService.class);
        bind(ImageService.class).to(GaeImageService.class);
        bind(AlbumRepository.class).to(ObjectifyAlbumRepository.class);
        bind(FlickrMetaDataRepository.class)
                .to(ObjectifyFlickrMetaDataRepository.class);
        bind(PhotoDataRepository.class).to(ObjectifyPhotoDataRepository.class);
        bind(PhotoRepository.class).to(ObjectifyPhotoRepository.class);
        bind(ScaledPhotoDataRepository.class)
                .to(ObjectifyScaledPhotoDataRepository.class);
    }
}
package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.domain.Image;
import com.sweetitech.hrm.repository.ImageRepository;
import com.sweetitech.hrm.service.ImageService;
import com.sweetitech.hrm.service.implementation.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;

	@Override
	public Image addImage(Image image) {
		
		Image i =findByUrl(image.getUrl());
		if(i==null) {
			return imageRepository.save(image);
		}
		return i;
	}

	@Override
	public void deleteImage(Image image) {
		
		imageRepository.delete(image);
		
	}

	@Override
	public Image findById(Long id) {
		
		return imageRepository.getFirstById(id);
	}

	@Override
	public Image findByUrl(String url) {
		
		return imageRepository.findByUrl(url);
	}

	@Override
	public Image updateImage(Image image) {
		// TODO Auto-generated method stub
		return imageRepository.save(image);
	}
	
	

}
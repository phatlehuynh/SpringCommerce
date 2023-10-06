import React, { useRef, useState } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const Carousel = ({ slides }) => {
  const aspectRatio = '16/9'; // Desired aspect ratio
  const [width, height] = aspectRatio.split('/').map(Number);

  const settings = {
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    
    autoplay: true,
    autoplaySpeed: 3000,
    
  };


  return (
    <div className="relative mt-5" >
      <Slider {...settings} >
        {slides.map((slide, index) => (
          <div key={index} className="">
            <div style={{ paddingBottom: `${(height / width) * 100}%` }} className="relative">
              <img
                src={slide.imageUrl}
                alt={slide.altText}
                className="absolute inset-0 w-full h-full object-contain rounded-md"
              />
            </div>
          </div>
        ))}
      </Slider>
    </div>
  );
};

export default Carousel;

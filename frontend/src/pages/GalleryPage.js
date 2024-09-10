import React from 'react';

const GalleryPage = () => {
  // Assuming you have a list of image URLs
  const images = [
    '/assets/image1.jpg',
    '/assets/image2.jpg',
    '/assets/image3.jpg',
  ];

  return (
    <div>
      <h2>Gallery</h2>
      <div className="gallery">
        {images.map((src, index) => (
          <img key={index} src={src} alt={`Gallery image ${index + 1}`} />
        ))}
      </div>
    </div>
  );
};

export default GalleryPage;

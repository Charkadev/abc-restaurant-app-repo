import React, { useState } from 'react';
import './GalleryPage.css'; // Import the CSS file for Gallery page

const GalleryPage = () => {
  const totalImages = 48; // Number of images
  const images = Array.from({ length: totalImages }, (_, index) => `/assets/gallery/gal${index + 1}.jpg`);

  // State to track the clicked image
  const [selectedImage, setSelectedImage] = useState(null);

  // Function to handle clicking an image
  const handleImageClick = (src) => {
    setSelectedImage(src);
  };

  // Function to close the modal
  const handleCloseModal = () => {
    setSelectedImage(null);
  };

  return (
    <div className="gallery-container">
      <h2 className="gallery-heading">Gallery</h2>
      <div className="gallery-grid">
        {images.map((src, index) => (
          <img
            key={index}
            src={src}
            alt={`Gallery image ${index + 1}`}
            className="gallery-image"
            onClick={() => handleImageClick(src)} // When image is clicked
          />
        ))}
      </div>

      {/* Modal to display selected image */}
      {selectedImage && (
        <div className="modal" onClick={handleCloseModal}>
          <div className="modal-content">
            <span className="close-button" onClick={handleCloseModal}>&times;</span>
            <img src={selectedImage} alt="Selected" className="modal-image" />
          </div>
        </div>
      )}
    </div>
  );
};

export default GalleryPage;

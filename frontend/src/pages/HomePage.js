import React from 'react';
import { Link } from 'react-router-dom';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css'; // Carousel styling
import './HomePage.css'; // Import CSS for styling

const HomePage = () => {
  return (
    <div className="homepage-container">
      {/* Carousel in the top half */}
      <div className="carousel-container">
        <Carousel autoPlay infiniteLoop showThumbs={false}>
          <div className="carousel-slide">
            <img src="/assets/carousel1.jpg" alt="Delicious Food 1" />
            <div className="carousel-text">
              <h1>Welcome to ABC Restaurant!</h1>
              <p>Discover delicious meals and place your orders online!</p>
              <Link to="/restaurants" className="carousel-link">Explore Restaurants</Link>
              <Link to="/login" className="carousel-link">Sign in to place an order</Link>
            </div>
          </div>
          <div className="carousel-slide">
            <img src="/assets/carousel2.jpg" alt="Delicious Food 2" />
            <div className="carousel-text">
              <h1>Welcome to ABC Restaurant!</h1>
              <p>Discover delicious meals and place your orders online!</p>
              <Link to="/restaurants" className="carousel-link">Explore Restaurants</Link>
              <Link to="/login" className="carousel-link">Sign in to place an order</Link>
            </div>
          </div>
        </Carousel>
      </div>

      {/* 3 Restaurant Images in the bottom half */}
      <div className="restaurant-images-container">
        <div className="restaurant-card">
          <img src="/assets/colombo.jpg" alt="ABC Restaurant Colombo" />
          <h3>ABC Restaurant Colombo</h3>
          <Link to="/restaurants/colombo" className="view-details-link">View Details</Link>
        </div>
        <div className="restaurant-card">
          <img src="/assets/kandy.jpg" alt="ABC Restaurant Kandy" />
          <h3>ABC Restaurant Kandy</h3>
          <Link to="/restaurants/kandy" className="view-details-link">View Details</Link>
        </div>
        <div className="restaurant-card">
          <img src="/assets/NuwaraEliya.jpg" alt="ABC Restaurant Kandy" />
          <h3>ABC Restaurant Nuwara Eliya</h3>
          <Link to="/restaurants/NuwaraEliya" className="view-details-link">View Details</Link>
        </div>
        <div className="restaurant-card">
          <img src="/assets/galle.jpg" alt="ABC Restaurant Galle" />
          <h3>ABC Restaurant Galle</h3>
          <Link to="/restaurants/galle" className="view-details-link">View Details</Link>
        </div>
      </div>
    </div>
  );
};

export default HomePage;

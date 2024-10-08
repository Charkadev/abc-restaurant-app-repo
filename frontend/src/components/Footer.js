import React from 'react';
import './Footer.css';
import { FaFacebook, FaInstagram, FaTwitter } from 'react-icons/fa'; // You need to install react-icons

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <p>&copy; 2024 ABC Restaurant. All Rights Reserved.</p>
        <ul className="footer-links">
          <li><a href="/about">About Us</a></li>
          <li><a href="/contact">Contact Us</a></li>
          <li><a href="/privacy">Privacy Policy</a></li>
        </ul>
        <div className="footer-socials">
          <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer">
            <FaFacebook /> Facebook
          </a> 
          <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer">
            <FaInstagram /> Instagram
          </a> 
          <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer">
            <FaTwitter /> Twitter
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

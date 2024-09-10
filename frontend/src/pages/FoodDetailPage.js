import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

const FoodDetailPage = () => {
  const { foodId } = useParams(); // Get food ID from the URL
  const [food, setFood] = useState({});
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchFoodDetails = async () => {
      try {
        const response = await api.get(`/api/foods/${foodId}`); // Replace with your backend endpoint
        setFood(response.data);
      } catch (err) {
        setError('Failed to load food details.');
      }
    };

    fetchFoodDetails();
  }, [foodId]);

  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>{food.name}</h2>
      <p>{food.description}</p>
      <p>Price: ${food.price}</p>
      <img src={food.image} alt={food.name} />
    </div>
  );
};

export default FoodDetailPage;

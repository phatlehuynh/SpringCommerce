import React, { useState, useEffect } from 'react';


const Filter = ({ keyword }) => {
  // state logic
  const [products, setProducts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [color, setColor] = useState('');
  const [brand, setBrand] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');

  const handleFilter = async () => {
    const url = `http://localhost:8080/api/products/page/filter?` +
    new URLSearchParams({
      keyword,
      brand,
      color,
      minPrice,
      maxPrice 
    });
    try {
      const response = await fetch(url, {
        method: 'GET',  
        headers: {
          'Content-Type': 'application/json'
        }
      });
  
      const data = await response.json();
  
      setProducts(data.data.elements);
      setTotalPages(data.totalPages);
  
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    fetchProducts();
  }, [ keyword]);

  const fetchProducts = async () => {
    handleFilter()
  }



  return (
    <div>
      <label>
        Color:
        <input 
          type="text"
          value={color}
          onChange={(e) => setColor(e.target.value)} 
        />
      </label>

      <label>
        Brand: 
        <input
          type="text" 
          value={brand}
          onChange={(e) => setBrand(e.target.value)}
        />
      </label>

      <label>
        Min Price:
        <input
          type="number"
          value={minPrice} 
          onChange={(e) => setMinPrice(e.target.value)}
        />
      </label>

      <label>
        Max Price:
        <input  
          type="number"
          value={maxPrice}
          onChange={(e) => setMaxPrice(e.target.value)}
        />  
      </label>

      <button
      
      onClick={() => handleFilter()}
      >Filter</button>
    </div>

    




  );
};

const styles = {
  filter: {
    display: 'flex',
    flexDirection: 'column',
    width: '300px',
    margin: '0 auto' 
  },

  label: {
    marginBottom: '10px'
  }
};

export default Filter;
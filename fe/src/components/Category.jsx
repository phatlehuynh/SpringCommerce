import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const Category = ({categoryId, setCategoryId}) => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/categories');
        const data = await response.json();
        setCategories(data.data); // Assuming the array is directly in the 'data' property
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []); // The empty dependency array ensures this effect runs only once after mount

  return (
    <div className='max-w-[1640px] m-auto px-4 py-12'>
      <h1 className='text-orange-600 font-bold text-4xl text-center'>
        Categories
      </h1>
      {/* Categories */}
      <div className='grid grid-cols-2 md:grid-cols-4 gap-6 py-6'>
        {categories.map((item) => (
          <div
              onClick={() => {setCategoryId(item.id)
                console.log(categoryId)
              }}>
            <Link to={`/category/${item.id}`} key={item.id}>
              <div
                className='bg-gray-100 rounded-lg p-4 flex justify-between items-center'
              >
                <h2 className='font-bold sm:text-xl'>{item.title}</h2>
                {/* Add more details if needed, such as item.description */}
              </div>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Category;

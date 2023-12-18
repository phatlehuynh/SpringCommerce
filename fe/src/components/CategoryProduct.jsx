import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';

const CategoryProduct = ({categoryId, setCategoryId}) => {
  const { id } = useParams();
  const [products, setProducts] = useState({ products: [] });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/products/page?categoryId=${id}`);
        console.log(response);

        const data = await response.json();
        setProducts( data.data.elements ); // Corrected to setProducts({ products: data });
        console.log(data.data.elements)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [id]);

  return (
    <div className='max-w-[1640px] m-auto px-4 py-12'>
      {/* Display Products */}
      <div className='grid grid-cols-2 lg:grid-cols-4 gap-6 pt-4 lg:pr-20 lg:pl-20'>
      {products.length > 0 ? (
        products.map((item) => (
          <div key={item.id} className='border shadow-lg rounded-lg hover:scale-105 duration-300'>
            {/* Product Image */}
            <Link to={`/product/${item.id}`} key={item.id}>
              <img
                src={item.linkImages[0]}
                alt={item.name} 
                className='w-full h-[350px] object-cover object-center rounded-t-lg'
              />
            </Link>
            {/* Product Details */}
            <div className='flex justify-between px-2 py-4'>
              <p className='font-bold'>{item.name}</p>  
              <p>
                <span className='bg-orange-500 text-white p-1 rounded-full'>
                  {item.price}
                </span>
              </p>
            </div>
            <div className='px-2'>
              <p className='text-gray-600'>{item.sumary}</p>
            </div>
            <div className='flex justify-between items-center px-2 py-4'>
              <p ></p>
              <button className='bg-blue-500 text-white px-3 py-1 rounded'>
                Add to Cart
              </button>
            </div>
          </div>
        ))
                  ) : (
                      <p>Loading data...</p>
                  )}

      </div>
    </div>
  );
};

export default CategoryProduct;

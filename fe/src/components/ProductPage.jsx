import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

const ProductPage = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [activeImg, setActiveImage] = useState(null);
  const [amount, setAmount] = useState(1);

  useEffect(() => {
    // Fetch data from your API
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/product/${id}`);
        const data = await response.json();
        setProduct(data.data);
        setActiveImage(data.data.linkImages[0]);

      } catch (error) {
        console.error('Error fetching product data:', error);
      }
    };

    fetchData();
  }, [id]); // Dependency array ensures useEffect runs whenever id changes

  if (!product) {
    // Loading state, you can customize this part
    return <div>Loading...</div>;
  }

  const { name, summary, price, linkImages, category } = product;

  const handleAddToCart = async () => {
    try {
      const token = localStorage.getItem('token'); 
      const userData = localStorage.getItem('user');
      const user = JSON.parse(userData);
      const userId = user.id;
      // const id = localStorage.getItem('user')
      // Gửi request để thêm sản phẩm vào giỏ hàng
      const response = await fetch(`http://localhost:8080/api/cart/addProductToCart?cartId=${user.cartId}&productId=${id}&quantity=${amount}`, {
        method: 'PUT'
      });

      if (response.ok) {
        // Xử lý nếu thêm vào giỏ hàng thành công
        alert('Product added to cart successfully!');
      } else {
        // Xử lý nếu có lỗi khi thêm vào giỏ hàng
        const error = await response.json();
        alert(`Failed to add product to cart: ${error.message}`);
      }
    } catch (error) {
      console.error('Error adding product to cart:', error);
      alert('sản phẩm đã tồn tại trong giỏ hàng');
    }
  };

  const handleAddToCart2 = async (productId, quantity) => {
    // Your existing handleAddToCart2 logic here
    // ...
  };

  return (
    <div className='flex flex-col lg:flex-col gap-16 items-center lg:pt-20 lg: pr-300'>
      {/* Display Images */}
      {/* Display Product Details */}
      <div className='flex flex-col gap-4 lg:w-2/4 '>
        <div className='flex flex-row gap-6  lg:pr-100'>
          <img
            src={activeImg}
            alt=""
            className='w-300px h-300px aspect-square object-cover rounded-xl border border-gray-300'
          />
          <div className='flex lg:flex-row flex-row justify-between h-50 w-50'>
            {linkImages.map((img, index) => (
              <img
                key={index}
                src={img}
                alt={`Product ${index + 1}`}
                className='w-50 h-24 rounded-md cursor-pointer border border-gray-300'
                onClick={() => setActiveImage(img)}
              />
            ))}
          </div>
        </div>
        <div>
          <span className='text-violet-600 font-semibold text-lg'>{category.name}</span>
          <h1 className='text-3xl font-bold mb-2'>{name}</h1>
        </div>
        <p className='text-gray-700 mb-4'>{summary}</p>
        <h6 className='text-2xl font-semibold mb-4'>{`$ ${price.toFixed(2)}`}</h6>

        {/* Quantity and Add to Cart */}
        <div className='flex flex-row items-center gap-12'>
          <div className='flex flex-row items-center'>
          <button
      className='bg-gray-200 py-2 px-4 rounded-lg text-violet-800 text-3xl'
      onClick={() => setAmount(prev => Math.max(prev - 1, 1))}
    >
      -
    </button>
    <span className='py-4 px-6 rounded-lg'>{amount}</span>
    <button
      className='bg-gray-200 py-2 px-4 rounded-lg text-violet-800 text-3xl'
      onClick={() => setAmount(prev => prev + 1)}
    >
      +
    </button>
  </div>
  <button
    className='bg-violet-800 text-white font-semibold py-3 px-16 rounded-xl h-full'
    onClick={handleAddToCart}
  >
    Add to Cart
  </button>
        </div>
      </div>

      <div className='grid grid-cols-2 lg:grid-cols-4 gap-6 pt-4 lg:pr-20 lg:pl-20 lg:pb-10'>
      </div>
    </div>
  );
};

export default ProductPage;
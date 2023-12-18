import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
  
const Product = () => {
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [totalProducts, setTotalProducts] = useState(0);
  const [Products, setProducts] = useState([]);
  const [quantities, setQuantities] = useState([]);



  useEffect(() => {
    // Fetch data from your API for the specified page
    fetch(`http://localhost:8080/api/products/page?pageIndex=${page}`)
      .then(response => response.json())
      .then(data => {setProducts(data.data.elements)
        console.log("xxxxxxxxxxx")
        console.log(data.totalPages)
        setTotalPages(data.data.totalPages)
      })
      .catch(error => console.error('Error fetching data:', error));
  }, [page]);

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setPage(newPage);
    }
  };

  const handleQuantityChange = (productId, amount) => {
  }

  const handleAddToCart = async (productId, quantity) => {
    // Get userId from Local Storage
    const userData = localStorage.getItem('user');

    // Check if userData is not null
    if (!userData) {
      alert('Bạn cần đăng nhập để sử dụng giỏ hàng');
      return; // Stop execution if userData is null
    }
  
    // Parse the user data
    const user = JSON.parse(userData);
  
    // Check if data has the 'id' property
    if (!user || !user.id) {
      alert('Không tìm thấy thông tin người dùng hợp lệ');
      return; // Stop execution if 'id' is not present in user
    }
  
    const userId = user.id;
  
    try {
      // Send a POST request to the API to add the product to the cart
      const response = await fetch(`http://localhost:8080/api/cart/addProductToCart?cartId=${user.cartId}&productId=${productId}&quantity=${quantity}`, {
        method: 'PUT'
      });
  
      if (response.ok) {
        alert('Thêm sản phẩm thành công');
        // Add any additional logic you need for a successful response
        // Redirect to ProductAdmin or other page after a successful update
      } else {
        const error = await response.json();
        console.error('Thêm sản phẩm thất bại', error);
        alert(`Thêm sản phẩm thất bại: ${error.message}`);
        // Handle error scenarios here
      }
    } catch (error) {
      console.error('Error:', error.message);
      alert(`Error: ${error.message}`);
    }
  };
  

  // Generate an array of page numbers for rendering pagination buttons
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <div className='max-w-[1640px] m-auto px-4 py-12'>
      <h1 className='text-orange-600 font-bold text-4xl text-center'>
        Top Rated Menu Items
      </h1>

{/* Display Products */}

<div className='grid grid-cols-2 lg:grid-cols-4 gap-6 pt-4 lg:pr-20 lg:pl-20'>
  {
    Products.map((product) => {
      let productId = product.id

      return (

        <div className='border shadow-lg rounded-lg hover:scale-105 duration-300' key={product.id}>
          {/* Product Image */}
          <Link to={`/product/${product.id}`}>
            <img
              src={product.linkImages[0]}
              alt={product.name}  
              className='w-full h-[350px] object-cover object-center rounded-t-lg'
            />
          </Link>
          {/* Product Details */}
          <div className='flex justify-between px-2 py-4'>
            <p className='font-bold'>{product.name}</p>  {/* Change 'title' to 'name' */}
            <p>
              <span className='bg-orange-500 text-white p-1 rounded-full'>
                {product.price}
              </span>
            </p>
          </div>
          <div className='px-2'>
            <p className='text-gray-600'>{product.summary}</p>
          </div>
          <div className='flex justify-between products-center px-2 py-4'> 
            <button className='bg-blue-500 text-white px-3 py-1 rounded'
              onClick={() => handleAddToCart(product.id, 1)}>
              Add to Cart
            </button>
          </div>
        </div>
      )
    })
  }
</div>


      {/* Pagination */}
      <div className='flex justify-center mt-4'>
        {pageNumbers.map((pageNumber) => (
          <button
            key={pageNumber}
            onClick={() => handlePageChange(pageNumber)}
            className={`m-1 border-orange-600 text-orange-600 hover:bg-orange-600 hover:text-white ${pageNumber === page && 'bg-orange-600 text-white'}`}
          >
            {pageNumber}
          </button>
        ))}
      </div>
    </div>
  );
};

export default Product;

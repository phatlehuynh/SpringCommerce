import React, { useState, useEffect } from 'react';
import { useParams, useNavigate,Link } from 'react-router-dom';

const Search = ({categoryId}) => {
  const [color, setColor] = useState('');
  const [brand, setBrand] = useState('');
  const [minPrice, setMinPrice] = useState(0);
  const [maxPrice, setMaxPrice] = useState(999999999);




  const { keyword } = useParams();
  const [products, setProducts] = useState([]);
  const [totalProducts, setTotalProducts] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const pageSize = 8; // Số lượng sản phẩm trên mỗi trang
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log(keyword)

        // Gọi API để lấy sản phẩm cho trang hiện tại
        const response = await fetch(`http://localhost:8080/api/products/page?keyword=${keyword}&pageIndex=${currentPage}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        const data = await response.json();
        setProducts(data.data.elements);
        setTotalPages(data.data.totalPages)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [keyword, currentPage]);

  const handleFilter = async () => {
    const url = `http://localhost:8080/api/products/page/filter?` +
    new URLSearchParams({
      categoryId,
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



  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  return (
    <div>
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

      {/* Hiển thị danh sách sản phẩm */}
      {/* Display Products */}
      <div className='grid grid-cols-2 lg:grid-cols-4 gap-6 pt-4  lg:pr-20  lg:pl-20'>
        {products.map((product) => (
          <Link to={`/product/${product.id}`} key={product.id}>
            <div className='border shadow-lg rounded-lg hover:scale-105 duration-300'>
              {/* Product Image */}
              <img
                src={product.linkImages[0]}
                alt={product.name}
                className='w-full h-[350px] object-cover object-center rounded-t-lg'
              />
              {/* Product Details */}
              <div className='flex justify-between px-2 py-4'>
                <p className='font-bold'>{product.name}</p>
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
                <p className='text-gray-500'>Quantity: {product.quantity}</p>
                <button className='bg-blue-500 text-white px-3 py-1 rounded'>
                  Add to Cart
                </button>
              </div>
            </div>
          </Link>
        ))}
      </div>
{/* Hiển thị nút phân trang */}
<div className="flex justify-center mt-4">
  {Array.from({ length: totalPages }, (_, index) => index + 1).map((page) => (
    <button
      key={page}
      onClick={() => handlePageChange(page)}
      className={`mx-2 px-4 py-2 rounded focus:outline-none ${
        page === currentPage
          ? 'bg-gray-500 text-white'
          : 'bg-gray-200 text-gray-800 hover:bg-gray-300'
      }`}
      disabled={page === currentPage}
    >
      {page}
    </button>
  ))}
</div>
    </div>
  );
};

export default Search;

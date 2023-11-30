
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const ProductAdmin = () => {
  const navigate = useNavigate();
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [totalProducts, setTotalProducts] = useState(0);
  const [products, setProducts] = useState([]);
  const [deleteMessage, setDeleteMessage] = useState(null);
  const [fetchDataTrigger, setFetchDataTrigger] = useState(false); // State để kích hoạt lại fetch dữ liệu

  // useEffect(() => {
  //   // Fetch total number of products from the API
  //   fetch('https://localhost:7076/api/Product/count')
  //     .then(response => response.json())
  //     .then(data => {
  //       setTotalProducts(data);
  //       setTotalPages(Math.ceil(data / 8));
  //     })
  //     .catch(error => console.error('Error fetching total number of products:', error));
  // }, [deleteMessage]); // Add deleteMessage to the dependency array

  useEffect(() => {
    // Fetch data from your API for the specified page
    fetch(`http://localhost:8080/api/products/page?pageIndex=${page}`)
      .then(response => response.json())
      .then(data => {
        setProducts(data.data.elements)
        setTotalPages(data.data.totalPages)
      })
      .catch(error => console.error('Error fetching data:', error));
  }, [page, deleteMessage, fetchDataTrigger]); // Add deleteMessage and fetchDataTrigger to the dependency array

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setPage(newPage);
    }
  };

  const handleDelete = async (productId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/api/product/delete/${productId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        // Set delete message to trigger useEffect and fetch updated data
        alert('Delete successfully!');
        setFetchDataTrigger((prev) => !prev); // Kích hoạt lại fetch dữ liệu
        navigate('/Product/Admin', { replace: true });
      } else {
        const error = await response.json();
        setDeleteMessage(`Failed to delete product: ${error.message}`);
      }
    } catch (error) {
      console.error('Error deleting product:', error);
      setDeleteMessage('Error deleting product. Please try again.');
    }
  };

  return (
    <div className="max-w-[1640px] m-auto px-4 py-12">
      <h1 className="text-orange-600 font-bold text-4xl text-center">
        Product List
      </h1>
      {deleteMessage && (
        <div className="bg-green-200 text-green-800 py-2 px-4 mt-2 rounded">
          {deleteMessage}
        </div>
      )}

      {/* Display Product List as a Simple Table */}
      <table className="min-w-full">
        <thead>
          <tr>
            <th className="border p-2">ID</th>
            <th className="border p-2">Name</th>
            <th className="border p-2">Price</th>
            <th className="border p-2">Summary</th>
            <th className="border p-2">Content</th>
            <th className="border p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
    {products.map((product) => (
      <tr key={product.id}>
        <td className="border p-2">{product.id}</td>
        <td className="border p-2">{product.name}</td>
        <td className="border p-2">{product.price}</td>
        <td className="border p-2">{product.summary}</td>
        <td className="border p-2">{product.content}</td>
        <td className="border p-2">
          <Link to={`/ProductUpdateForm/${product.id}`}>
            <button
              className="bg-blue-500 text-white px-3 py-1 rounded mr-2"
            >
              Update
            </button>
          </Link>
          <button
            className="bg-red-500 text-white px-3 py-1 rounded"
            onClick={() => handleDelete(product.id)}
          >
            Delete
          </button>
        </td>
      </tr>
    ))}
        </tbody>
      </table>
            {/* Add Button */}
            <div className="mt-4">
        <Link to="/Product/Form" className="bg-green-500 text-white px-3 py-1 rounded">
          Add Product
        </Link>
      </div>

      {/* Pagination */}
      <div className="flex justify-center mt-4">
        {Array.from({ length: totalPages }, (_, i) => i + 1).map((pageNumber) => (
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
export default ProductAdmin;

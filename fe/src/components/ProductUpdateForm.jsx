import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const ProductUpdateForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [product, setProduct] = useState({
    name: '',
    summary: '',
    content: '',
    price: '',
    brand: '',
    color:'',
    linkImages: [],
    categoryId: '',
  });

  const [productImages, setProductImages] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');

  useEffect(() => {
    // Fetch categories from API
    fetch('http://localhost:8080/api/categories')
      .then((response) => response.json())
      .then((data) => {
        setCategories(data.data);
      })
      .catch((error) => console.error('Error fetching categories:', error));

    if (id) {
      fetch(`http://localhost:8080/api/product/${id}`)
        .then((response) => response.json())
        .then((data) => {
          setProduct(data.data);
          setSelectedCategory(data.categoryId);
        })
        .catch((error) => console.error('Error fetching product details:', error));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const files = e.target.files;
    setProductImages(files);
  };

  const handleCategoryChange = (e) => {
    const selectedCategoryId = e.target.value;
    setSelectedCategory(selectedCategoryId);

    // Set the categoryId in the product state
    setProduct((prevProduct) => ({
      ...prevProduct,
      categoryId: selectedCategoryId,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {

      if(productImages.length > 0) {
        product.linkImages = []
        for (let i = 0; i < productImages.length; i++) {
          const formDataImage = new FormData();
          formDataImage.append('file', productImages[i]);
          console.log(formDataImage)
  
          const response = await fetch('http://localhost:8080/api/file/insert', {
            method: 'POST',
            body: formDataImage,
          });
      
          if (response.ok) {
            const result = await response.json();
            if(!product.linkImages) {
              product.linkImages = []
            }
            product.linkImages.push(result.data)
            setProduct(product)
            alert('Product Image added successfully!');
          } else {
            const error = await response.json();
            alert(`Failed to add product Image: ${error.message}`);
          }
        }
      }
      // Lấy mã thông báo JWT từ localStorage
      const token = localStorage.getItem('token');
      // ...
  
      // Xác định xem có tạo mới hay cập nhật sản phẩm dựa trên sự có mặt của productId
      const endpoint = `http://localhost:8080/api/product/update/${product.id}`;
      // ...
  
      // Gửi yêu cầu PUT đến máy chủ với chi tiết sản phẩm và hình ảnh
      const response = await fetch(endpoint, {
        method: 'PUT',
        body: JSON.stringify(product),
        headers: {
          "Content-Type" : "application/json",
          Authorization: `Bearer ${token}`, // Bao gồm mã thông báo JWT trong tiêu đề
        },
      });
  
      // Kiểm tra xem yêu cầu có thành công không (mã trạng thái 2xx)
      if (response.ok) {
        // Phân tích cú pháp JSON phản hồi
        const result = await response.json();
        console.log('Sản phẩm đã được cập nhật:', result);
        alert('Sản phẩm đã được cập nhật thành công!');
        navigate('/Product/Admin', { replace: true }); // Chuyển hướng đến ProductAdmin sau khi cập nhật thành công
      } else {
        // Nếu yêu cầu không thành công, phân tích cú pháp JSON lỗi
        const error = await response.json();
        console.error('Không thể cập nhật sản phẩm:', error);
        // Xử lý các tình huống lỗi ở đây
      }
    } catch (error) {
      // Xử lý bất kỳ lỗi nào có thể xảy ra trong quá trình này
      console.error('Lỗi khi cập nhật sản phẩm:', error);
      // Xử lý lỗi mạng hoặc các lỗi khác ở đây
    }
  };
  
  

  return (
    <div className="container mx-auto mt-8">
      <form onSubmit={handleSubmit} className="max-w-lg mx-auto bg-white p-8 shadow-md">
        <h2 className="text-2xl font-bold mb-6">Update Product</h2>

        {/* Display Product ID */}
        <div className="mb-4">
          <label htmlFor="id" className="block text-sm font-medium text-gray-600">
            Product ID
          </label>
          <input
            type="text"
            id="id"
            name="id"
            value={product.id}
            className="mt-1 p-2 w-full border rounded-md"
            readOnly
          />
        </div>

        <div className="mb-4">
          <label htmlFor="name" className="block text-sm font-medium text-gray-600">
            Name
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={product.name}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="description" className="block text-sm font-medium text-gray-600">
            Summary
          </label>
          <textarea
            id="description"
            name="summary"
            value={product.summary}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            rows="3"
            required
          ></textarea>
        </div>
        <div className="mb-4">
          <label htmlFor="description" className="block text-sm font-medium text-gray-600">
            Content
          </label>
          <textarea
            id="content"
            name="summary"
            value={product.content}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            rows="3"
            required
          ></textarea>
        </div>

        <div className="mb-4">
          <label htmlFor="price" className="block text-sm font-medium text-gray-600">
            Price
          </label>
          <input
            type="number"
            id="price"
            name="price"
            value={product.price}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="brand" className="block text-sm font-medium text-gray-600">
            Brand
          </label>
          <input
            id="brand"
            name="brand"
            value={product.brand}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="color" className="block text-sm font-medium text-gray-600">
            Color
          </label>
          <input
            id="color"
            name="color"
            value={product.color}
            onChange={handleChange}
            className="mt-1 p-2 w-full border rounded-md"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="categoryId" className="block text-sm font-medium text-gray-600">
            Category
          </label>
          <select
            id="categoryId"
            name="categoryId"
            value={selectedCategory}
            onChange={handleCategoryChange}
            className="mt-1 p-2 w-full border rounded-md"
            required
          >
            <option value="" disabled>Select a category</option>
            {console.log(categories)}
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.title}
              </option>
            ))}
          </select>
        </div>

        <div className="mb-4">
          <label htmlFor="productImages" className="block text-sm font-medium text-gray-600">
            Images
          </label>
          <input
            type="file"
            id="productImages"
            name="productImages"
            onChange={handleImageChange}
            className="mt-1 p-2 w-full border rounded-md"
            multiple // Allow multiple files to be selected
          />
        </div>

        <div className="mb-4">
          <button
            type="submit"
            className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
          >
            Update Product
          </button>
        </div>
      </form>
    </div>
  );
};

export default ProductUpdateForm;

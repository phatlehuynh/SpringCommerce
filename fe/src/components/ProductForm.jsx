import { Link } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ProductForm = () => {
  const navigate = useNavigate();
  const [product, setProduct] = useState({
    name: '',
    summary: '',
    content: '',
    price: '',
    brand: '',
    color:'',
    categoryId: '',
    linkImages: []
  });

  const [productImages, setProductImages] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');

  const [formReset, setFormReset] = useState(false); // State để theo dõi trạng thái của form sau khi thành công

  useEffect(() => {
    // Fetch categories from API
    fetch('http://localhost:8080/api/categories')  // Update the API endpoint
      .then((response) => response.json())
      .then((data) => {
        setCategories(data.data);
      })
      .catch((error) => console.error('Error fetching categories:', error));
  }, []);

  useEffect(() => {
    // Nếu formReset trở thành true, làm mới form và đặt lại formReset về false
    if (formReset) {
      setProduct({
        name: '',
        summary: '',
        content: '',
        price: '',
        brand: '',
        color:'',
        categoryId: '',
        linkImages: []
      });
      setProductImages([]);
      setSelectedCategory('');
      setFormReset(false);
    }
  }, [formReset]);

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

    setProduct((prevProduct) => ({
      ...prevProduct,
      categoryId: selectedCategoryId,
    }));
  };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();

  //   try {
  //     const formData = new FormData();

  //     formData.append('title', product.title);
  //     formData.append('description', product.description);
  //     formData.append('price', product.price);
  //     formData.append('quantity', product.quantity);
  //     formData.append('categoryId', product.categoryId);

  //     for (let i = 0; i < productImages.length; i++) {
  //       formData.append('productImages', productImages[i]);
  //     }

  //     const response = await fetch('https://localhost:7076/api/Product/create', {
  //       method: 'POST',
  //       body: formData,
  //     });

  //     if (response.ok) {
  //       const result = await response.json();
  //       alert('Product added successfully!');
  //       setFormReset(true); // Thực hiện làm mới form
  //     } else {
  //       const error = await response.json();
  //       alert(`Failed to add product: ${error.message}`);
  //     }
  //   } catch (error) {
  //     alert('An error occurred while adding the product. Please try again later.');
  //   }
  // };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
  
      // Append product images to formData
      for (let i = 0; i < productImages.length; i++) {
        console.log(productImages[i])
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
          setFormReset(true); // Trigger form reset
        } else {
          const error = await response.json();
          alert(`Failed to add product Image: ${error.message}`);
        }
      }
      // Get the JWT token from localStorage
      const token = localStorage.getItem('token'); 
      console.log(token);
      console.log(product)
      const response = await fetch('http://localhost:8080/api/product/insert', {
        method: 'POST',
        body: JSON.stringify(product),
        headers: {
          "Content-Type" : "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (response.ok) {
        const result = await response.json();
        alert('Product added successfully!');
        setFormReset(true); // Trigger form reset
      } else {
        const error = await response.json();
        alert(`Failed to add product: ${error.message}`);
      }
    } catch (error) {
      alert('An error occurred while adding the product. Please try again later.');
      console.log(error);
    }
  };
  
  

  return (
    <div className="container mx-auto mt-8">
      <form onSubmit={handleSubmit} className="max-w-lg mx-auto bg-white p-8 shadow-md">
        <h2 className="text-2xl font-bold mb-6">Add Product</h2>

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
          <label htmlFor="content" className="block text-sm font-medium text-gray-600">
            Content
          </label>
          <textarea
            id="content"
            name="content"
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
            Add Product
          </button>
          <Link to="/Product/Admin">
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
          >
            BACK TO ADMIN
          </button>
          </Link>

        </div>
      </form>
    </div>
  );
};

export default ProductForm;

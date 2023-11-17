import React, { useEffect, useState }  from 'react'
import { useParams } from "react-router-dom";
import MainLayout from '../layout/MainLayout'
import ImageCard from '../components/ImageCard'
import Carousel from '../components/Carousel';
import axios from 'axios';

const ProductSection = ()=> {
    const { productId } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        // Fetch data for the specific product using Axios
        axios.get(`http://localhost:8080/api/product/${productId}`)
          .then((response) => setProduct(response.data))
          .catch((error) => console.error('Error fetching product:', error));
      }, [productId]);

    if (!product) {
        return <p>Loading...</p>;
    }

  return (
    <div className=' lg:w-10/12 mx-auto'>
        <div>
            <div className='flex flex-row'>
                <div className='w-8/12 h-screen'>
                    <div className='container '>
                        <img src="https://via.placeholder.com/500" alt="testing" />
                    </div>
                </div>
                <div className='w-4/12 '>
                    <h2 className="text-3xl font-semibold mb-4">{product.name}</h2>
                    <p className="text-gray-700">{product.description}</p>
                    <p className="text-xl mt-4">${product.price}</p>
                    <button className="mt-4 bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded">
                    Add to Cart
                    </button>
                </div>
            </div>
        </div>
    </div>  
)}

const RelatedProductSection = ()=> {
    const [relatedProducts, setRelatedProducts] = useState([]);

    useEffect(() => {
        fetchData();
      }, []);
    
      const fetchData = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/product'); // Adjust the URL according to your API endpoint
          setRelatedProducts(response.data);
        } catch (error) {
          console.error('Error fetching related products:', error);
        }
      };
    console.log(relatedProducts)

  return (
    <div className=' lg:w-11/12 mx-auto'>
        <div className='flex flex-col py-10'>
            <div className='flex flex-row pb-5'>
                <div >
                    <h2 className='text-xl'>You Might Also Like</h2>
                </div>
            </div>
            <div>
                <Carousel slides={relatedProducts.map((product) => (
                    <ImageCard
                        key={product.id}
                        product={product}
                    />
                ))} 
                slidesToShow={4}/>
            </div>
        </div>
    </div>  
)}

export default function Product(){
    return (
        <MainLayout>
            <ProductSection/>
            <RelatedProductSection/>
        </MainLayout>
    )
}
import { useState, useEffect } from 'react';
import ImageCard from './components/ImageCard';
import axios from 'axios';
import MainLayout from './layout/MainLayout';
// import BannerCarousel from './components/BannerCarousel';
import { Provider } from 'react-redux'
import {store} from './redux/store.js'


const BannerSection =()=>{
  // const slides = [
  //   { imageUrl: '/images/shoes2.jpg', altText: 'Image 1' },
  //   { imageUrl: '/images/shoes3.jpg', altText: 'Image 2' },
  //   { imageUrl: '/images/shoes4.jpg', altText: 'Image 3' },
  //   { imageUrl: '/images/shoes2.jpg', altText: 'Image 4' },
  //   { imageUrl: '/images/shoes3.jpg', altText: 'Image 5' },
  //   { imageUrl: '/images/shoes4.jpg', altText: 'Image 6' },

  //   // Add more slides as needed
  // ];
  return(
    <div className=' lg:w-10/12 mx-auto'>
      <div className='w-4/12'>
      
      </div>
      <div className='w-8/12'>
        {/* <BannerCarousel slides={slides} />  */}
      </div>
    </div>
  )
};
const ApplySection = () => {
  

  const [data, setData] = useState('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/product'); // Adjust the URL according to your setup
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };
    console.log(data)
  
  return (
    <> 
    <div className='lg:w-10/12 mx-auto'>
      <div className="mt-6 grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-6">
                  {data.length > 0 ? (
                      data.map((dataAll) => (
                          <ImageCard
                              key={dataAll.id}
                              product={dataAll}
                          />
                      ))
                  ) : (
                      <p>Loading data...</p>
                  )}
        </div>
    </div>
      
    </>
  )
}

export default function App(){
  return (
    <Provider store={store}>
        <MainLayout>
          <BannerSection/>
          <ApplySection/>
        </MainLayout>
    </Provider>
    
  );
}

import { useState } from 'react';
import ProductCard from '../components/ProductCard';
import Sidebar from '../components/Sidebar';
import { productsArray } from '../data/products';

const HomePage = () => {
  const [showSidebar, setShowSidebar] = useState(true);
  const [products] = useState(productsArray);

  const toggleSidebar = () => {
    setShowSidebar(!showSidebar);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-end items-center mb-8">
        <div className="flex gap-4">
          <button 
            className="flex items-center gap-2 hover:text-gray-700"
            onClick={toggleSidebar}
          >
            {showSidebar ? 'Hide' : 'Show'} Filters
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="M6 13.5V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m12-3V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m-6-9V3.75m0 3.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 9.75V10.5" />
            </svg>
          </button>
          <button className="flex items-center gap-2">
            Sort By
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
            </svg>
          </button>
        </div>
      </div>

      <div className="flex gap-8">
        <div className={`transition-all duration-300 ${
          showSidebar 
            ? 'w-64 opacity-100' 
            : 'w-0 opacity-0 overflow-hidden'
        }`}>
          <Sidebar />
        </div>
        
        <div className={`flex-1 transition-all duration-300 ${
          showSidebar ? 'ml-0' : 'ml-0'
        }`}>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {products.map(product => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage; 
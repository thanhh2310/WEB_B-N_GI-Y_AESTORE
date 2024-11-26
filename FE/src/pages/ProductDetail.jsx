import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import { products } from '../data/products';

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [selectedSize, setSelectedSize] = useState(null);
  const [selectedColor, setSelectedColor] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = () => {
      const foundProduct = products[id];
      if (foundProduct) {
        setProduct(foundProduct);
        setSelectedColor(foundProduct.colors[0]);
      } else {
        toast.error('Không tìm thấy sản phẩm');
      }
    };

    fetchProduct();
  }, [id]);

  const addToCart = () => {
    if (!selectedSize) {
      toast.error('Vui lòng chọn size');
      return;
    }

    if (!selectedColor) {
      toast.error('Vui lòng chọn màu');
      return;
    }

    const cartItem = {
      productId: product.id,
      name: product.name,
      price: product.price,
      color: selectedColor,
      size: selectedSize,
      quantity: quantity,
      image: product.images[0]
    };

    const currentCart = JSON.parse(localStorage.getItem('cart') || '[]');
    const existingItemIndex = currentCart.findIndex(
      item => item.productId === cartItem.productId && 
             item.color === cartItem.color && 
             item.size === cartItem.size
    );

    if (existingItemIndex !== -1) {
      currentCart[existingItemIndex].quantity += quantity;
    } else {
      currentCart.push(cartItem);
    }

    localStorage.setItem('cart', JSON.stringify(currentCart));
    toast.success('Đã thêm vào giỏ hàng!');
  };

  const buyNow = () => {
    if (!selectedSize) {
      toast.error('Vui lòng chọn size');
      return;
    }
    
    // Thêm vào giỏ hàng
    addToCart();
    // Chuyển đến trang thanh toán
    navigate('/checkout');
  };

  const renderStars = (rating) => {
    return [...Array(5)].map((_, index) => (
      <svg
        key={index}
        className={`w-5 h-5 ${index < rating ? 'text-yellow-400' : 'text-gray-300'}`}
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
      </svg>
    ));
  };

  const getColorStyle = (color) => {
    const colorMap = {
      'Black': 'bg-black',
      'White': 'bg-white border-2',
      'Red': 'bg-red-600',
      'Blue': 'bg-blue-600',
      'Pink': 'bg-pink-400',
      'Black/White': 'bg-gradient-to-r from-black to-white',
      'Red/White': 'bg-gradient-to-r from-red-600 to-white',
      'Blue/White': 'bg-gradient-to-r from-blue-600 to-white',
      'White/Red': 'bg-gradient-to-r from-white to-red-600',
      'White/Black': 'bg-gradient-to-r from-white to-black',
    };

    return colorMap[color] || 'bg-gray-200';
  };

  if (!product) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">Đang tải...</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Product Images */}
        <div className="space-y-4">
          <div className="aspect-square overflow-hidden rounded-lg">
            <img
              src={product.images[0]}
              alt={product.name}
              className="w-full h-full object-cover"
            />
          </div>
          <div className="grid grid-cols-3 gap-4">
            {product.images.slice(1).map((image, index) => (
              <div key={index} className="aspect-square overflow-hidden rounded-lg">
                <img
                  src={image}
                  alt={`${product.name} ${index + 2}`}
                  className="w-full h-full object-cover"
                />
              </div>
            ))}
          </div>
        </div>

        {/* Product Info */}
        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold">{product.name}</h1>
            <p className="text-xl mt-2">{product.price.toLocaleString('vi-VN')}đ</p>
          </div>

          <div>
            <h2 className="font-semibold mb-2">Mô tả</h2>
            <p className="text-gray-600">{product.description}</p>
          </div>

          <div>
            <h2 className="font-semibold mb-2">Màu sắc</h2>
            <div className="flex gap-4">
              {product.colors.map((color, index) => (
                <div
                  key={index}
                  className={`flex flex-col items-center gap-2 cursor-pointer`}
                  onClick={() => setSelectedColor(color)}
                >
                  <div
                    className={`w-8 h-8 rounded-full ${getColorStyle(color)} 
                      ${selectedColor === color 
                        ? 'ring-2 ring-offset-2 ring-black' 
                        : 'hover:ring-2 hover:ring-offset-2 hover:ring-gray-300'}`}
                  />
                  <span className={`text-sm ${selectedColor === color ? 'font-medium' : ''}`}>
                    {color}
                  </span>
                </div>
              ))}
            </div>
          </div>

          <div>
            <h2 className="font-semibold mb-2">Size</h2>
            <div className="grid grid-cols-4 gap-2">
              {product.sizes.map((size) => (
                <button
                  key={size}
                  className={`px-4 py-2 border rounded-lg hover:border-black
                    ${selectedSize === size ? 'border-black bg-black text-white' : ''}`}
                  onClick={() => setSelectedSize(size)}
                >
                  {size}
                </button>
              ))}
            </div>
          </div>

          <div>
            <h2 className="font-semibold mb-2">Số lượng</h2>
            <div className="flex items-center gap-4">
              <button
                className="px-3 py-1 border rounded-lg"
                onClick={() => setQuantity(Math.max(1, quantity - 1))}
              >
                -
              </button>
              <span>{quantity}</span>
              <button
                className="px-3 py-1 border rounded-lg"
                onClick={() => setQuantity(quantity + 1)}
              >
                +
              </button>
            </div>
          </div>

          <div className="flex gap-4">
            <button
              onClick={addToCart}
              className="flex-1 py-3 border-2 border-black text-black rounded-full hover:bg-gray-100 transition-colors"
            >
              Thêm vào giỏ
            </button>
            <button
              onClick={buyNow}
              className="flex-1 py-3 bg-black text-white rounded-full hover:bg-gray-800 transition-colors"
            >
              Mua ngay
            </button>
          </div>

          <div className="space-y-2 text-sm text-gray-600">
            <p>Danh mục: {product.category}</p>
            <p>Thương hiệu: {product.brand}</p>
          </div>

          {/* Detailed Description */}
          <div className="border-t pt-6">
            <h2 className="text-xl font-semibold mb-4">Mô tả chi tiết</h2>
            <ul className="space-y-2">
              {product.detailDescription?.map((desc, index) => (
                <li key={index} className="flex items-start gap-2">
                  <svg className="w-5 h-5 text-green-500 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
                  </svg>
                  <span>{desc}</span>
                </li>
              ))}
            </ul>
          </div>

          {/* Reviews */}
          <div className="border-t pt-6">
            <h2 className="text-xl font-semibold mb-4">Đánh giá từ khách hàng</h2>
            <div className="space-y-4">
              {product.reviews?.map((review) => (
                <div key={review.id} className="border-b pb-4">
                  <div className="flex items-center gap-2 mb-2">
                    <span className="font-medium">{review.user}</span>
                    <div className="flex">
                      {renderStars(review.rating)}
                    </div>
                    <span className="text-gray-500 text-sm">
                      {new Date(review.date).toLocaleDateString('vi-VN')}
                    </span>
                  </div>
                  <p className="text-gray-600">{review.comment}</p>
                </div>
              ))}
            </div>
          </div>

          <div className="text-sm text-gray-600">
            {selectedColor && selectedSize && (
              <p>
                Đã chọn: {selectedColor} - Size {selectedSize}
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail; 
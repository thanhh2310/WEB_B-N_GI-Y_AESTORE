import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import axios from 'axios';

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedSize, setSelectedSize] = useState(null);
  const [selectedColor, setSelectedColor] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [reviewForm, setReviewForm] = useState({
    rating: 5,
    comment: '',
    images: []
  });
  const [reviews, setReviews] = useState([]);
  const [sizes, setSizes] = useState([]);
  const [colors, setColors] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/saleShoes/products/${id}`);
        const productData = response.data?.result;
        if (productData) {
          setProduct({
            id: productData.id,
            name: productData.name,
            description: productData.description,
            price: productData.minPrice || 0,
            images: productData.imageUrl ? [productData.imageUrl] : ['/path/to/default/image.jpg'],
            category: productData.category?.name,
            brand: productData.brand?.name,
            sizes: ['38', '39', '40', '41', '42'],
            colors: ['Black', 'White', 'Red']
          });
        } else {
          toast.error('Không tìm thấy sản phẩm');
        }
      } catch (error) {
        console.error('Error fetching product:', error);
        toast.error('Không tìm thấy sản phẩm');
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/saleShoes/products/${id}/reviews`);
        setReviews(response.data?.result || []);
      } catch (error) {
        console.error('Error fetching reviews:', error);
      }
    };

    if (product) {
      fetchReviews();
    }
  }, [id, product]);

  useEffect(() => {
    const fetchSizesAndColors = async () => {
      try {
        // Fetch sizes
        const sizesResponse = await axios.get('http://localhost:8081/saleShoes/sizes');
        if (sizesResponse.data?.result) {
          setSizes(sizesResponse.data.result);
        }

        // Fetch colors
        const colorsResponse = await axios.get('http://localhost:8081/saleShoes/colors');
        if (colorsResponse.data?.result) {
          setColors(colorsResponse.data.result);
        }
      } catch (error) {
        console.error('Error fetching sizes and colors:', error);
      }
    };

    fetchSizesAndColors();
  }, []);

  const addToCart = async () => {
    if (!selectedSize) {
      toast.error('Vui lòng chọn size');
      return;
    }

    if (!selectedColor) {
      toast.error('Vui lòng chọn màu');
      return;
    }

    try {
      await axios.post('http://localhost:8081/saleShoes/cart/add', {
        productId: product.id,
        quantity: quantity,
        color: selectedColor,
        size: selectedSize
      });
      toast.success('Đã thêm vào giỏ hàng!');
    } catch (error) {
      console.error('Error adding to cart:', error);
      toast.error('Có lỗi xảy ra khi thêm vào giỏ hàng');
    }
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

  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files);
    const imagePromises = files.map(file => {
      return new Promise((resolve) => {
        const reader = new FileReader();
        reader.onload = (e) => resolve(e.target.result);
        reader.readAsDataURL(file);
      });
    });

    Promise.all(imagePromises).then(images => {
      setReviewForm(prev => ({
        ...prev,
        images: [...prev.images, ...images].slice(0, 3) // Giới hạn 3 ảnh
      }));
    });
  };

  const removeImage = (index) => {
    setReviewForm(prev => ({
      ...prev,
      images: prev.images.filter((_, i) => i !== index)
    }));
  };

  const submitReview = () => {
    if (!reviewForm.comment.trim()) {
      toast.error('Vui lòng nhập nội dung đánh giá');
      return;
    }

    const newReview = {
      id: Date.now(),
      ...reviewForm,
      user: 'Khách hàng', // Sau này sẽ lấy từ user đăng nhập
      date: new Date().toISOString(),
      likes: 0,
      productId: id
    };

    const updatedReviews = [newReview, ...reviews];
    setReviews(updatedReviews);
    localStorage.setItem(`reviews_${id}`, JSON.stringify(updatedReviews));

    // Reset form
    setReviewForm({
      rating: 5,
      comment: '',
      images: []
    });
    setShowReviewForm(false);
    toast.success('Cảm ơn bạn đã đánh giá sản phẩm!');
  };

  const likeReview = (reviewId) => {
    const updatedReviews = reviews.map(review => {
      if (review.id === reviewId) {
        return { ...review, likes: review.likes + 1 };
      }
      return review;
    });
    setReviews(updatedReviews);
    localStorage.setItem(`reviews_${id}`, JSON.stringify(updatedReviews));
  };

  // Tính rating trung bình
  const averageRating = reviews.length > 0
    ? (reviews.reduce((sum, review) => sum + review.rating, 0) / reviews.length).toFixed(1)
    : 0;

  if (loading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-gray-900"></div>
        </div>
      </div>
    );
  }

  if (!product) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="text-center">Không tìm thấy sản phẩm</div>
      </div>
    );
  }

  const reviewSection = (
    <div className="mt-12 border-t pt-8">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Đánh giá sản phẩm</h2>
        <button
          onClick={() => setShowReviewForm(!showReviewForm)}
          className="px-6 py-2 bg-black text-white rounded-full hover:bg-gray-800"
        >
          Viết đánh giá
        </button>
      </div>

      {/* Rating Summary */}
      <div className="flex items-center gap-8 mb-8">
        <div className="text-center">
          <div className="text-4xl font-bold">{averageRating}</div>
          <div className="flex gap-1 my-2">
            {[1, 2, 3, 4, 5].map((star) => (
              <svg
                key={star}
                className={`w-5 h-5 ${
                  star <= averageRating ? 'text-yellow-400' : 'text-gray-300'
                }`}
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118l-2.8-2.034c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
              </svg>
            ))}
          </div>
          <div className="text-sm text-gray-500">{reviews.length} đánh giá</div>
        </div>

        {/* Rating Distribution */}
        <div className="flex-grow">
          {[5, 4, 3, 2, 1].map((star) => {
            const count = reviews.filter(review => review.rating === star).length;
            const percentage = reviews.length > 0 ? (count / reviews.length) * 100 : 0;
            
            return (
              <div key={star} className="flex items-center gap-2">
                <div className="text-sm w-8">{star} sao</div>
                <div className="flex-grow bg-gray-200 rounded-full h-2">
                  <div
                    className="bg-yellow-400 rounded-full h-2"
                    style={{ width: `${percentage}%` }}
                  />
                </div>
                <div className="text-sm w-12">{count}</div>
              </div>
            );
          })}
        </div>
      </div>

      {/* Review Form */}
      {showReviewForm && (
        <div className="bg-gray-50 p-6 rounded-lg mb-8">
          <h3 className="font-semibold mb-4">Viết đánh giá của bạn</h3>
          
          {/* Rating Input */}
          <div className="mb-4">
            <div className="flex gap-1">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  key={star}
                  onClick={() => setReviewForm(prev => ({ ...prev, rating: star }))}
                  className={`w-8 h-8 ${
                    star <= reviewForm.rating ? 'text-yellow-400' : 'text-gray-300'
                  }`}
                >
                  <svg fill="currentColor" viewBox="0 0 20 20">
                    <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118l-2.8-2.034c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                  </svg>
                </button>
              ))}
            </div>
          </div>

          {/* Comment Input */}
          <textarea
            value={reviewForm.comment}
            onChange={(e) => setReviewForm(prev => ({ ...prev, comment: e.target.value }))}
            placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm..."
            className="w-full p-4 border rounded-lg mb-4 min-h-[100px]"
          />

          {/* Image Upload */}
          <div className="mb-4">
            <div className="flex gap-4 mb-2">
              {reviewForm.images.map((image, index) => (
                <div key={index} className="relative">
                  <img
                    src={image}
                    alt={`Review ${index + 1}`}
                    className="w-20 h-20 object-cover rounded"
                  />
                  <button
                    onClick={() => removeImage(index)}
                    className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full w-5 h-5 flex items-center justify-center"
                  >
                    ×
                  </button>
                </div>
              ))}
              {reviewForm.images.length < 3 && (
                <label className="w-20 h-20 border-2 border-dashed rounded flex items-center justify-center cursor-pointer">
                  <input
                    type="file"
                    accept="image/*"
                    multiple
                    className="hidden"
                    onChange={handleImageUpload}
                  />
                  <svg className="w-6 h-6 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                  </svg>
                </label>
              )}
            </div>
            <p className="text-sm text-gray-500">Tối đa 3 ảnh</p>
          </div>

          <button
            onClick={submitReview}
            className="w-full py-3 bg-black text-white rounded-full hover:bg-gray-800"
          >
            Gửi đánh giá
          </button>
        </div>
      )}

      {/* Reviews List */}
      <div className="space-y-6">
        {reviews.map((review) => (
          <div key={review.id} className="border-b pb-6">
            <div className="flex justify-between mb-2">
              <div>
                <span className="font-medium">{review.user}</span>
                <div className="flex gap-1 my-1">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <svg
                      key={star}
                      className={`w-4 h-4 ${
                        star <= review.rating ? 'text-yellow-400' : 'text-gray-300'
                      }`}
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118l-2.8-2.034c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                    </svg>
                  ))}
                </div>
              </div>
              <span className="text-sm text-gray-500">
                {new Date(review.date).toLocaleDateString('vi-VN')}
              </span>
            </div>

            <p className="text-gray-700 mb-4">{review.comment}</p>

            {/* Review Images */}
            {review.images.length > 0 && (
              <div className="flex gap-4 mb-4">
                {review.images.map((image, index) => (
                  <img
                    key={index}
                    src={image}
                    alt={`Review ${index + 1}`}
                    className="w-20 h-20 object-cover rounded"
                  />
                ))}
              </div>
            )}

            {/* Like Button */}
            <button
              onClick={() => likeReview(review.id)}
              className="flex items-center gap-1 text-gray-500 hover:text-blue-600"
            >
              <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
              </svg>
              <span>{review.likes}</span>
            </button>
          </div>
        ))}
      </div>
    </div>
  );

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Product Images */}
        <div className="space-y-4">
          <div className="aspect-square overflow-hidden rounded-lg bg-gray-100">
            <img
              src={product.images[0]}
              alt={product.name}
              className="w-full h-full object-cover"
              onError={(e) => {
                e.target.src = '/path/to/default/image.jpg'; // Fallback image
              }}
            />
          </div>
        </div>

        {/* Product Info */}
        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold">{product.name}</h1>
            <p className="text-xl mt-2">
              {product.price > 0 ? `${product.price.toLocaleString('vi-VN')}đ` : 'Liên hệ'}
            </p>
          </div>

          {product.description && (
            <div>
              <h2 className="font-semibold mb-2">Mô tả</h2>
              <p className="text-gray-600">{product.description}</p>
            </div>
          )}

          {/* Category and Brand */}
          <div className="space-y-2 text-sm text-gray-600">
            {product.category && <p>Danh mục: {product.category}</p>}
            {product.brand && <p>Thương hiệu: {product.brand}</p>}
          </div>

          {/* Colors */}
          {colors.length > 0 && (
            <div>
              <h2 className="font-semibold mb-2">Màu sắc</h2>
              <div className="flex gap-4">
                {colors.map((color) => (
                  <button
                    key={color.id}
                    className={`px-4 py-2 border rounded-lg hover:border-black
                      ${selectedColor === color.name ? 'border-black bg-black text-white' : ''}`}
                    onClick={() => setSelectedColor(color.name)}
                  >
                    {color.name}
                  </button>
                ))}
              </div>
            </div>
          )}

          {/* Sizes */}
          {sizes.length > 0 && (
            <div>
              <h2 className="font-semibold mb-2">Size</h2>
              <div className="grid grid-cols-4 gap-2">
                {sizes.map((size) => (
                  <button
                    key={size.id}
                    className={`px-4 py-2 border rounded-lg hover:border-black
                      ${selectedSize === size.name ? 'border-black bg-black text-white' : ''}`}
                    onClick={() => setSelectedSize(size.name)}
                  >
                    {size.name}
                  </button>
                ))}
              </div>
            </div>
          )}

          {/* Quantity */}
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

          {/* Action Buttons */}
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
        </div>
      </div>

      {/* Review section remains the same */}
      {reviewSection}
    </div>
  );
};

export default ProductDetail; 
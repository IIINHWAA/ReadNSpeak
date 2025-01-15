'use client'

import { Suspense, useEffect, useState } from 'react'; 
import { FaThumbsUp } from "react-icons/fa6";
import { Review } from '../../../types'; 
import { User } from '../../../types'; 
import { useParams } from 'next/navigation';  


async function getReviewData(id: string): Promise<Review | null> {
  const res = await fetch(`http://localhost:3000/data/review.json`);
  const reviews: Review[] = await res.json();
  return reviews.find(review => review.review_id.toString() === id) || null;
}


async function getUserData(userId: number): Promise<User | null> {
  const res = await fetch('http://localhost:3000/data/user.json');
  const users: User[] = await res.json();
  return users.find(user => user.user_id === userId) || null;
}

export default function ReviewDetailPage() {
  const { id } = useParams();  

  const reviewId = Array.isArray(id) ? id[0] : id;  

  const [review, setReview] = useState<Review | null>(null);
  const [user, setUser] = useState<User | null>(null);
  const [isLiked, setIsLiked] = useState<boolean>(false);

  useEffect(() => {
    if (reviewId) {  
      const fetchData = async () => {
        const fetchedReview = await getReviewData(reviewId);  
        if (fetchedReview) {
          setReview(fetchedReview);
          const fetchedUser = await getUserData(fetchedReview.user_id);
          setUser(fetchedUser);
        }
      };
      fetchData();
    }
  }, [reviewId]);  
  
  const handleLikeToggle = () => {
    setIsLiked(prevState => !prevState);  
  };

  if (!review || !user) {
    return <div>리뷰나 사용자 정보를 불러오는 중입니다...</div>;
  }

  return (
    <Suspense fallback = {<div>로딩 중...</div>}>
    <div className="w-4/5 mx-auto bg-gray-100 shadow-xl p-5 rounded-md mt-10">
      <div className="flex justify-between items-center">
        <div>
          <span className="text-lg mr-2 font-bold">{user.username}</span>
          <span className="text-sm text-gray-600">{review.created_at}</span>
        </div>
        <div className="flex items-center">
          <div className='flex flex-row items-center'>
            <span className="text-2xl">😊</span>
            <span className='ml-2 text-xl'>{review.rating}/5</span>
          </div>
        </div>
      </div>
      <p className="mt-5 text-base">{review.content}</p>
      <div className='flex flex-col items-center gap-1 mt-10'>
        <FaThumbsUp 
          className={`text-3xl ${isLiked ? 'text-green-500' : 'text-black'}`}
          onClick={handleLikeToggle}  
        />
        <span>{review.helpful_count}</span>
      </div>
    </div>
    </Suspense>
  );
}

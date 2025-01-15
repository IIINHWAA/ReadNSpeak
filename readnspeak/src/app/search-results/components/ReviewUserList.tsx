'use client';
import { useEffect, useState } from 'react';
import { Review } from '@/types';
import { User } from '@/types';
import Image from 'next/image';
import Link from 'next/link';

interface ReviewUserListProps {
  id: number; 
}

const ReviewUserList = ({ id }: ReviewUserListProps) => {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [users, setUsers] = useState<User[]>([]);

  const fetchReviews = async () => {
    const res = await fetch('/data/review.json');
    const data: Review[] = await res.json();
    const filteredReviews = data.filter((review) => review.book_id === id);
    setReviews(filteredReviews);
  };

  const fetchUsers = async () => {
    const res = await fetch('/data/user.json');
    const data: User[] = await res.json();
    setUsers(data);
  };

  useEffect(() => {
    fetchReviews();
    fetchUsers();
  }, [id]);

  const getUsernameById = (userId: number) => {
    const user = users.find((user) => user.user_id === userId);
    return user ? user.username : '알 수 없음';
  };

  return (
    <div>
      <div className="flex flex-row items-center justify-between">
        <p className="font-bold text-xl">독후평을 남긴 사람들</p>
        <button className="bg-gray-300 text-white rounded-lg px-4 py-2">독후평 등록</button>
      </div>

      <div className="grid grid-cols-4 gap-4 mt-4">
        {reviews.length === 0 ? (
          <p>이 책에 대한 리뷰가 없습니다.</p>
        ) : (
          reviews.map((review) => (
            <div key={review.user_id} className="flex flex-col items-center mt-10">
              <Link href={`/reviews/${review.review_id}`}>
              <div className="flex flex-col items-center">
                <Image
                  src="/img/user.jpg"
                  alt={getUsernameById(review.user_id)}
                  width={80}
                  height={80}
                  className="rounded-full mb-2"
                />
                <span>{getUsernameById(review.user_id)}</span>
              </div></Link>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default ReviewUserList;

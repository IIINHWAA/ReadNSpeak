'use client';  // 클라이언트 전용 컴포넌트임을 명시
import React, { useState, useEffect } from 'react';
import { FaAngleLeft, FaAngleRight } from "react-icons/fa";

const carouselImages = [
  { //일단 무료 이미지 사용
    id: 0,
    img_url: "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    img_name: "이미지1",
    src: "https://iiinhwaa.github.io/",
  },
  {
    id: 1,
    img_url: "https://images.unsplash.com/photo-1414124488080-0188dcbb8834?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    img_name: "이미지2",
    src: "/",
  },
  {
    id: 2,
    img_url:
      "https://images.unsplash.com/photo-1506962240359-bd03fbba0e3d?q=80&w=1730&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    img_name: "이미지3",
    src: "/",
  },
];

export default function EventBanner() {
  const [currentIndex, setCurrentIndex] = useState(0);

  // 다음 이미지로 이동
  const handlerNext = () => {
    setCurrentIndex((nextIndex) =>
      nextIndex === carouselImages.length - 1 ? 0 : nextIndex + 1
    );
  };

  // 이전 이미지로 이동
  const handlerPrevious = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === 0 ? carouselImages.length - 1 : prevIndex - 1
    );
  };

  // 자동으로 슬라이드 변경 (3초마다)
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((count) =>
        count === carouselImages.length - 1 ? 0 : count + 1
      );
    }, 3000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  return (
    <div className="relative w-full mx-auto">
      <div className="relative w-full overflow-hidden">
        <div
          className="flex transition-transform duration-500 ease-in-out"
          style={{
            transform: `translateX(-${currentIndex * 100}%)`,
          }}
        >
          {carouselImages.map((img) => (
            <div
              key={img.id}
              className="w-full flex justify-center items-center"
              style={{ flexShrink: 0 }}
            >
              <a href={img.src} className="w-full h-full">
                <img
                  src={img.img_url}
                  alt={img.img_name}
                  className="w-full h-[400px] rounded-lg" 
                />
              </a>
            </div>
          ))}
        </div>
      </div>

      <button
        onClick={handlerPrevious}
        className="absolute left-0 top-1/2 transform -translate-y-1/2 text-white"
      >
        <FaAngleLeft size={30} />
      </button>

      <button
        onClick={handlerNext}
        className="absolute right-0 top-1/2 transform -translate-y-1/2 text-white"
      >
        <FaAngleRight size={30} />
      </button>
    </div>
  );
}

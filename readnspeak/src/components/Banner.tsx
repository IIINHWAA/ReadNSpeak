'use client' // 클라이언트 전용 컴포넌트임을 명시
import React, { useState, useEffect } from 'react'
import { FaAngleLeft, FaAngleRight } from 'react-icons/fa'
import Image from 'next/image'

const carouselImages = [
  {
    //일단 무료 이미지 사용
    id: 0,
    img_url: '/img/FE_po.png',
    img_name: '이미지1',
    src: 'https://iiinhwaa.github.io/',
  },
  {
    id: 1,
    img_url: '/img/FE_po.png',
    img_name: '이미지2',
    src: '/',
  },
  {
    id: 2,
    img_url: '/img/FE_po.png',
    img_name: '이미지3',
    src: '/',
  },
]

export default function EventBanner() {
  const [currentIndex, setCurrentIndex] = useState(0)

  // 다음 이미지로 이동
  const handlerNext = () => {
    setCurrentIndex((nextIndex) =>
      nextIndex === carouselImages.length - 1 ? 0 : nextIndex + 1,
    )
  }

  // 이전 이미지로 이동
  const handlerPrevious = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === 0 ? carouselImages.length - 1 : prevIndex - 1,
    )
  }

  // 자동으로 슬라이드 변경 (3초마다)
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((count) =>
        count === carouselImages.length - 1 ? 0 : count + 1,
      )
    }, 3000)

    return () => {
      clearInterval(interval)
    }
  }, [])

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
                <Image
                  src={img.img_url}
                  alt={img.img_name}
                  width={1740} 
                height={1000} 
                layout="responsive" 
                  className="w-full h-[500px] rounded-lg"
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
  )
}

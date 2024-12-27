'use client'

import Banner from '@/components/Banner'
import CategoryBtn from '@/components/CategoryBtn'
import CategoryResults from '@/components/CategoryResult'
import SearchBar from '@/components/SearchBar'
import { useState } from 'react'

export default function Home() {
  //일단 임시로 구현 후 나중에 도서 api 사용 시 수정
  const categories = [
    '잡지, 만화',
    '여행, 취미',
    '일본 서적',
    '외국어',
    '외국 서적',
    '유아 아동',
    '중고 학습',
    '경제, 경영',
  ]

  return (
    <div>
      <Banner />
      <div className="flex flex-col items-center justify-center mt-16">
        <span className="font-extrabold text-xl">
          오늘도 다양한 도서를 만나보세요!
        </span>
      </div>
      <div className="mt-5">
        <SearchBar size="large" shape="rounded-f" color="none" shadow="full" />
      </div>

      <CategoryBtn categories={categories} />
    </div>
  )
}

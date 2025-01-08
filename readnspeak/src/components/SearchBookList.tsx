'use client'

import React from 'react'
import { FaStar } from 'react-icons/fa'
import Image from 'next/image'

// 이 곳에 api 연결해서 책 DB 구현
const SearchResultBookList = [
  {
    id: 0,
    img_url: '/img/XL.jpeg',
    img_name: '소년이 온다',
    img_author: '한강',
    rating: '4.5',
    comments: '12,003',
    description: `《소년이 온다》는 한강 작가가 2014년에 발표한 장편소설로, 1980년 광주 민주화 운동을 배경으로 그 시대를 살아간 사람들의 고통과 기억을 그립니다. 이야기는 친구 정대의 죽음을 목격한 중학생 동호를 중심으로 전개되며, 당시의 비극적인 사건들과 그 여파 속에서 남겨진 사람들의 삶을 조명합니다.
소설은 각 인물의 시점에서 진행되며, 동호와 그의 가족, 친구들, 그리고 다른 생존자들이 겪는 트라우마와 슬픔을 통해 인간의 존엄성과 역사적 비극의 잔상을 섬세하게 풀어냅니다. 책은 희생자들의 목소리를 대변하며, 광주에서 벌어진 잔혹한 폭력과 그 후 남겨진 이들의 고통을 잊지 말라는 메시지를 전달합니다`,
  },

  {
    id: 1,
    img_url: '/img/han.png',
    img_name: '채식주의자',
    img_author: '한강',
    rating: '4.2',
    comments: '42,003',
    description: `《채식주의자》는 한강의 작품으로, 한 여성이 자신만의 신념을 지키기 위해 사회적 규범과 가족의 기대를 거부하는 이야기를 그립니다.
평범한 가정주부인 영혜는 어느 날 갑자기 고기를 먹지 않겠다고 결심하고, 이로 인해 가족과의 갈등, 그리고 사회와의 단절이 시작됩니다.
이 소설은 인간 존재의 내면과 욕망, 그리고 자유를 향한 갈망을 탐구하며, 세 부분으로 나누어 각기 다른 시점에서 이야기를 전개합니다.`,
  },
]

const SearchBookList = () => {
  return (
    <div>
      {/* 검색결과 */}
      <div className="flex flex-col mt-10 text-lg">
        <div className="flex flex-row mb-5 items-center">
          <span className="font-bold  text-lg">검색 결과</span>
          <span className="ml-1 text-sm">
            ({SearchResultBookList.length}개)
          </span>
        </div>

        <div className="border-b border-gray-400  w-full mb-10"></div>
        {/* 나중에 페이지네이션 추가해야 함 */}

        {SearchResultBookList.map((book) => (
          <div key={book.id} className="flex flex-col">
            <div className="flex flex-row mb-10">
              <Image
                src={book.img_url}
                alt={book.img_name}
                className="w-[20%] h-auto rounded-md shadow-xl"
              />

              <div className="flex flex-col ml-10 gap-2">
                <h3 className="font-bold text-base">{book.img_name}</h3>
                <p className="text-gray-500 text-base">{book.img_author}</p>
                <div className="flex flex-row items-center">
                  <FaStar className="text-red-800 text-sm" />
                  <p className="text-red-800 ml-1 text-sm">{book.rating}</p>
                  <p className="text-gray-500 ml-1 text-sm">
                    ({book.comments})
                  </p>
                </div>
                <p className="text-black text-xs font-bold">
                  {book.description}
                </p>
              </div>
            </div>
            <div className="border-b border-gray-400 w-full mb-10"></div>
          </div>
        ))}
      </div>
    </div>
  )
}
export default SearchBookList

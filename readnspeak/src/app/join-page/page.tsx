import React from 'react'
import { SiNaver } from "react-icons/si";
import { RiKakaoTalkFill } from "react-icons/ri";
import Image from 'next/image';

const JoinPage = () => {
  return (
     <div className="flex items-center justify-center mt-10">
      <div className="flex flex-col md:w-80 lg:w-96">
        <span className="font-bold text-xl mb-4 text-center">회원가입</span>

        <input
          type="text"
          placeholder="아이디"
          className="px-5 py-3 border border-gray-200 rounded-t-lg"
        />

        <input
          type="password"
          placeholder="비밀번호"
          className="px-5 py-3 border border-t-0 border-gray-200 "
        />

        <input
          type="passwordCheck"
          placeholder="비밀번호 확인"
          className="px-5 py-3 border border-t-0 border-gray-200 rounded-b-lg"
        />


        <button className="bg-gray-400 text-white font-bold p-3 rounded-md mt-7">
          회원가입
        </button>
       
        {/* 나중에 소셜 로그인 용 버튼으로 수정 */}
        <div className='flex flex-row mt-6 items-center justify-center gap-4 mb-10'>
            <button className='bg-green-500 rounded-full p-3 flex items-center justify-center'>
              <SiNaver size={20} className="text-white" />
            </button>
            <button className='bg-yellow-400 rounded-full p-3 flex items-center justify-center'>
              <RiKakaoTalkFill size={24} />
            </button>
            <button className='bg-white border border-gray-200 rounded-full p-3 flex items-center justify-center'>
              <Image 
                src='/img/google.png' 
                alt="Google" 
                width={24}  
                height={24} 
                className="rounded-full" 
              />
            </button>
        </div>
       
      </div>
    </div>
  )
}

export default JoinPage

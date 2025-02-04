'use client'

import { useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import { FiSmile } from 'react-icons/fi';
import { useRouter } from 'next/navigation';

const AddReviewPage = () => {
  const [chosenEmoji, setChosenEmoji] = useState<string | null>(null); 
  const [showPicker, setShowPicker] = useState<boolean>(false); 
  const router = useRouter();

  const handleEmojiClick = (emojiObject: { emoji: string }) => {
    setChosenEmoji(emojiObject.emoji); 
    setShowPicker(false); 
  };

  const togglePicker = () => {
    setShowPicker(!showPicker); 
  };

  const handleBackBtn = () => {
    router.back();
  }

  return (
    <div className="w-4/5 mx-auto bg-gray-100 shadow-xl p-5 rounded-md mt-10 ">
      <div className="flex flex-col items-center justify-center mt-5">
        <span className="font-bold text-xl">✏️ 독후평 작성</span>

        <div className="flex flex-row mt-5 w-full justify-between">
          <input
            className="w-[90%] rounded-lg py-1 px-4"
            placeholder="제목을 입력해 주세요."
            type="text"
          />
          <button
            onClick={togglePicker}
            className=" text-3xl mr-2"
          >
            {chosenEmoji ? chosenEmoji : <FiSmile />}
          </button>
        </div>

        <div className="w-full h-full mt-5">
  <textarea 
    className="w-full h-80 p-4 rounded-lg resize-none"
    placeholder="독후평을 입력해주세요"
  />
</div>

        {showPicker && (
          <div className="mt-4">
            <EmojiPicker onEmojiClick={handleEmojiClick} />
          </div>
        )}
      </div>

      <div className='flex flex-row gap-4 items-center justify-end mr-1 mt-4'>
        <button onClick={handleBackBtn} className='px-4 py-1 rounded-md shadow-lg bg-white'>취소</button>
        <button className='px-4 py-1 rounded-md shadow-lg bg-blue-200'>작성</button>
      </div>
    </div>
  );
};

export default AddReviewPage;

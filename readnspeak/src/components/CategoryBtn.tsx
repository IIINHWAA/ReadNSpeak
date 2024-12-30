// src/components/CategoryBtn.tsx
'use client'

import { useRouter } from 'next/navigation'

interface CategoryBtnProps {
  categories: string[]
}

const CategoryBtn: React.FC<CategoryBtnProps> = ({ categories }) => {
  const router = useRouter()

  const handleCategoryClick = (category: string) => {
    router.push(`/category-results?category=${encodeURIComponent(category)}`)
  }

  return (
    <div className="grid grid-cols-4 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-4 mt-10">
      {categories.map((category, index) => (
        <button
          key={category}
          onClick={() => handleCategoryClick(category)}
          className="flex items-center space-x-4 px-4 py-2 font-bold"
        >
          <div className="bg-gray-300 rounded-full w-14 h-14 flex items-center justify-center  font-extrabold text-2xl text-black">
            {String.fromCharCode(65 + index)}
          </div>

          <span className="text-sm text-black">{category}</span>
        </button>
      ))}
    </div>
  )
}

export default CategoryBtn

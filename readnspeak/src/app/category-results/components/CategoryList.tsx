'use client'


import { useSearchParams } from 'next/navigation'

const CategoryList: React.FC = () => {
  const searchParams = useSearchParams()
  const category = searchParams.get('category')

  return (
    <div>
      <h1 className="text-2xl font-bold mt-10">📚{category}</h1>
      <p>아직</p>
    </div>
  )
}

export default CategoryList

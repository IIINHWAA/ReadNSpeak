'use client'
import { useRouter } from 'next/navigation'

const NavBar = () => {
  const router = useRouter()

  const handleHomeBtn = () => {
    router.push(`/`)
  }
  return (
    <nav className="bg-white  h-20">
      <div className="min-w-[750px] lg:w-[70%]  w-full md:w-[80%] mx-auto flex justify-between items-center h-full">
        <button
          className="text-black font-extrabold text-xl"
          onClick={handleHomeBtn}
        >
          읽고말해
        </button>

        <span>로그인</span>
      </div>
    </nav>
  )
}

export default NavBar

'use client'
import { useRouter } from 'next/navigation'

const NavBar = () => {
  const router = useRouter()

  const handleHomeBtn = () => {
    router.push(`/`)
  }

  const handleLoginBtn = () => {
    router.push(`/login-page`)
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

        <button
          className="text-black"
          onClick={handleLoginBtn}
        >
          로그인
        </button>
      </div>
    </nav>
  )
}

export default NavBar

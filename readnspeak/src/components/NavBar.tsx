import Link from 'next/link';

const NavBar = () => {
  return (
    <nav className="bg-white  h-20">
      <div className="w-full md:w-[80%] mx-auto flex justify-between items-center h-full">
        <span className="font-extrabold text-xl text-black">읽고말해</span>
        <span>로그인</span>
      </div>
    </nav>
  );
};

export default NavBar;
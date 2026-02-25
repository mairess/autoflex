import React from "react";

const Footer: React.FC = () => {
  return (
    <footer className="bg-white border-t mt-10">
      <div className="max-w-6xl mx-auto px-6 py-4 text-center text-sm text-gray-500">
        &copy; {new Date().getFullYear()} Inventory Production App. All rights reserved.
      </div>
    </footer>
  );
};

export default Footer;
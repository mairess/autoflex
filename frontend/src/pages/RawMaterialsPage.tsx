import { useEffect } from "react";

import { useAppDispatch, useAppSelector } from "../store/hooks";
import { fetchRawMaterials } from "../store/slices/rawMaterialSlice";
import RawMaterialForm from "../components/RawMaterials/RawMaterialForm";
import RawMaterialItem from "../components/RawMaterials/RawMaterialItem";

const RawMaterialsPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const { items } = useAppSelector((s) => s.rawMaterials);

  useEffect(() => {
    dispatch(fetchRawMaterials());
  }, [dispatch]);

  return (
    <div className="bg-white rounded-xl shadow-sm border p-6 space-y-6">
      <h1 className="text-2xl font-bold text-gray-800">
      Raw Materials
      </h1>

      <RawMaterialForm />

      <div className="grid md:grid-cols-2 gap-4">
        {items.map((rm) => (
          <RawMaterialItem key={rm.id} rawMaterial={rm} />
        ))}
      </div>
    </div>
  );
};

export default RawMaterialsPage;
import React from "react";
import { useSelector } from "react-redux";

import type { RootState } from "../../store";
import RawMaterialItem from "./RawMaterialItem";

const RawMaterialsList: React.FC = () => {
  const rawMaterials = useSelector((state: RootState) => state.rawMaterials.items);

  return (
    <div>
      <h2>Raw Materials List</h2>
      <ul>
        {rawMaterials.map((rawMaterial) => (
          <RawMaterialItem key={rawMaterial.id} rawMaterial={rawMaterial} />
        ))}
      </ul>
    </div>
  );
};

export default RawMaterialsList;
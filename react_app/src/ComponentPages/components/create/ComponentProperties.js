import {z} from "zod";
import {api} from "../../../main.jsx";

export const fetchSupp = async (userId) => {
    const [typesResponse, tagsResponse, setsResponse] = await Promise.all([
        api.get("/api/types/get/all"),
        api.get("/api/tags/get/all"),
        api.get(`/api/sets/get/${userId}`)
    ]);
    return {
        types: typesResponse.data,
        tags: tagsResponse.data,
        sets: [...setsResponse.data, {name: '+Add new set', id: -1}],
    };
};

export const ValidationSchema = (suppData) => z.object({
    name: z.string().min(3, "Name must be longer than 2"),
    type: z.string().refine((val) => suppData?.types.some((t) => t.name === val), "Select valid type"),
    set: z.string().refine((val) => suppData?.sets.some((s) => s.name === val), "Select valid set"),
    hex: z.string().regex(/^#[0-9A-Fa-f]{6}$/, "Hex color must be valid")
});
import Navigation from "./Navigation/Navigation.jsx";
import Sorting from "./ComponentPages/components/list/filters/Sorting.jsx";
import Types from "./ComponentPages/components/list/filters/Types.jsx";
import FilterButton from "./ComponentPages/components/list/filters/FilterButton.jsx";
import SearchBar from "./ComponentPages/components/list/filters/SearchBar.jsx";

const HomePage = () => {
    return (
        <>
            <Navigation/>
            <h1 style={{marginTop: "7rem"}}>Home</h1>
            <SearchBar/>
            <Sorting/>
            <Types/>
            <FilterButton/>
        </>
    )
}

export default HomePage;
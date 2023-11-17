import { useFormik } from "formik";
import * as Yup from "yup";
import Carousel from '../components/Carousel';
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api/logIn";


export default function Home() {
  //const router = useHistory();
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const formik = useFormik({
    initialValues: {
      name: "",
      password: "",
      
    },

    validationSchema: Yup.object({
      name: Yup.string()
        .required("Enter your username"),
      password: Yup.string()
        .required("Enter your password"),
    }),

    onSubmit: (values) => {
      console.log("form submitted");
      console.log(values);

      const newUser = {
        username: values.name,
        password: values.password
      }

      loginUser(newUser, dispatch, navigate)
      //router.push({ pathname: "/success", query: values });
    },
  });

  const slides = [
    <img src="/images/shoes2.jpg" alt="Image 1" className="object-cover" key="1"/>,
    <img src="/images/shoes3.jpg" alt="Image 2" className="object-cover" key="2"/>,
    <img src="/images/shoes4.jpg" alt="Image 3" className="object-cover" key="3"/>,

    // Add more slides as needed
  ];

  return (
    
    <div className="absolute w-full h-screen items-center flex justify-center bg-[#00CCCC] ">
        <form
          onSubmit={formik.handleSubmit}
          className="bg-white flex rounded-lg w-2/3 font-latoRegular p-10"
        >
          <div className="flex flex-col text-gray-700 mr-10 w-1/2">
            <h1 className="text-xl pb-2 font-latoBold">
              Sign In
            </h1>
            <p className="text-sm  text-gray-500">
              Your Account For Everything Nice
            </p>
            <div className="mt-6 ">
              {/* Name input field */}
              <div className="pb-4">
                <label
                  htmlFor="name"
                  className={`block font-latoBold text-sm pb-2 ${
                    formik.touched.name && formik.errors.name
                      ? "text-red-400"
                      : ""
                  } `}
                >
                  {formik.touched.name && formik.errors.name
                    ? formik.errors.name
                    : "Name"}
                </label>
                <p className="text-sm font-latoBold text-red-400 "></p>
                <input
                  className="border-2 border-gray-500 p-2 rounded-md w-full focus:border-teal-500 focus:ring-teal-500 "
                  type="text"
                  name="name"
                  placeholder="Enter your Username"
                  onChange={formik.handleChange}
                  value={formik.values.name}
                  onBlur={formik.handleBlur}
                />
              </div>
              {/* Email input field */}
              <div className="pb-4">
                <label
                  htmlFor="password"
                  className={`block font-latoBold text-sm pb-2 ${
                    formik.touched.password && formik.errors.password
                      ? "text-red-400"
                      : ""
                  }`}
                >
                  {formik.touched.password && formik.errors.password
                    ? formik.errors.password
                    : "Password"}
                </label>

                <p className="text-sm font-latoBold text-red-400 "></p>
                <input
                  className="border-2 border-gray-500 p-2 rounded-md w-full focus:border-teal-500 focus:ring-teal-500"
                  type="password"
                  name="password"
                  placeholder="Enter your Password"
                  onChange={formik.handleChange}
                  value={formik.values.password}
                  onBlur={formik.handleBlur}
                />
              </div>
              
              <button
                type="submit"
                className="bg-teal-500 font-latoBold text-sm text-white py-3 mt-6 rounded-lg w-full"
              >
                Sign In
              </button>
            </div>
          </div>
          <div className="w-1/2">
            <Carousel slides={slides} className={"w-full h-70 overflow-hidden"} slidesToShow={1}/> 

            {/* <img src="/images/shoes2.jpg" alt="" className="object-cover rounded-lg"/> */}
          </div>
        </form>
    </div>
  );
}
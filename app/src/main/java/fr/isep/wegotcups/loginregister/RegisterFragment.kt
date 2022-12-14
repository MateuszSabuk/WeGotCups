package fr.isep.wegotcups.loginregister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.isep.wegotcups.R
import fr.isep.wegotcups.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }

        binding.buttonRegister.setOnClickListener {
            // TODO input check
            val email: String = binding.emailTextField.editText?.text.toString()
            val password: String = binding.passwordTextField.editText?.text.toString()
            Log.d("email", email)
            Log.d("password", password)
            if (activity is LoginRegisterActivity) {
                (activity as LoginRegisterActivity).createAccount(email,password)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package fr.isep.wegotcups.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            val email: String = binding.emailTextField.editText?.text.toString()
            val name: String = binding.nameTextField.editText?.text.toString()
            val password: String = binding.passwordTextField.editText?.text.toString()
            val repPass: String = binding.repeatPasswordTextField.editText?.text.toString()
            if (name.isEmpty() || name.isBlank()) {
                Toast.makeText(context, getString(R.string.wrongNewName),
                    Toast.LENGTH_SHORT).show()
            }
            else if (!isValidEmail(email)){
                Toast.makeText(context, getString(R.string.wrongNewEmail),
                    Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(
                    context, getString(R.string.shortNewPassword),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password != repPass) {
                Toast.makeText(
                    context, getString(R.string.wrongRepeatPassword),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (activity is LoginRegisterActivity) {
                    (activity as LoginRegisterActivity).createAccount(email,password)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidEmail(email: String) : Boolean{
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}